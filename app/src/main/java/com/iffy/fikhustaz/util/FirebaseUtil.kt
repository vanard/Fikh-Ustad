package com.iffy.fikhustaz.util

import android.content.Context
import android.util.Log
import android.util.Log.d
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.iffy.fikhustaz.data.itemviews.ChatItem
import com.iffy.fikhustaz.data.itemviews.ImageMessageItem
import com.iffy.fikhustaz.data.itemviews.PersonItem
import com.iffy.fikhustaz.data.itemviews.TextMessageItem
import com.iffy.fikhustaz.data.model.*
import com.iffy.fikhustaz.data.model.chat.*
import com.xwray.groupie.kotlinandroidextensions.Item

object FirebaseUtil {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().currentUser?.uid
            ?: throw NullPointerException("UID is null.")}")

    private val chatChannelsCollectionRef = firestoreInstance.collection("chatChannels")

    fun initCurrentUserIfFirstTime(ustad: Ustad, onComplete: () -> Unit) {
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                val newUser = ustad
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            }
            else
                onComplete()
        }
    }

    fun updateCurrentUser(nama: String = "",
                          email: String = "",
                          handphone: String = "",
                          tempatLahir: String = "",
                          tanggalLahir: String = "",
                          pendidikan: String = "",
                          keilmuan: String = "",
                          firkah: String = "",
                          mazhab: String = "",
                          profilePicture: String? = null,
                          sertifikat: String? = null,
                          ijazah: String? = null,
                          rate: String? = null
    ) {
        val userFieldMap = mutableMapOf<String, Any>()
        if (nama.isNotBlank()) userFieldMap["nama"] = nama
        if (email.isNotBlank()) userFieldMap["email"] = email
        if (handphone.isNotBlank()) userFieldMap["handphone"] = handphone
        if (tempatLahir.isNotBlank()) userFieldMap["tempatLahir"] = tempatLahir
        if (tanggalLahir.isNotBlank()) userFieldMap["tanggalLahir"] = tanggalLahir
        if (pendidikan.isNotBlank()) userFieldMap["pendidikan"] = pendidikan
        if (keilmuan.isNotBlank()) userFieldMap["keilmuan"] = keilmuan
        if (firkah.isNotBlank()) userFieldMap["firkah"] = firkah
        if (mazhab.isNotBlank()) userFieldMap["mazhab"] = mazhab
        if (profilePicture != null)
            userFieldMap["profilePicture"] = profilePicture
        if (sertifikat != null)
            userFieldMap["sertifikat"] = sertifikat
        if (ijazah != null)
            userFieldMap["ijazah"] = ijazah
        currentUserDocRef.update(userFieldMap)
    }

    fun getCurrentUser(onComplete: (Ustad) -> Unit) {
        currentUserDocRef.get()
            .addOnSuccessListener {
                onComplete(it.toObject(Ustad::class.java)!!)
            }
    }

    fun addUsersListener(context: Context, onListen: (List<Item>) -> Unit): ListenerRegistration {
        return firestoreInstance.collection("users")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "Users listener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                val items = mutableListOf<Item>()
                querySnapshot!!.documents.forEach {
                    if (it.id != FirebaseAuth.getInstance().currentUser?.uid)
                        items.add(PersonItem(it.toObject(Ustad::class.java)!!, it.id, context))
                }
                onListen(items)
            }
    }

    fun removeListener(registration: ListenerRegistration) = registration.remove()

    fun getOrCreateChatChannel(otherUserId: String,
                               onComplete: (channelId: String) -> Unit) {
        currentUserDocRef.collection("engagedChatChannels")
            .document(otherUserId).get().addOnSuccessListener {
                if (it.exists()) {
                    onComplete(it["channelId"] as String)
                    return@addOnSuccessListener
                }

                val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

                val newChannel = chatChannelsCollectionRef.document()
                newChannel.set(
                    ChatChannel(
                        mutableListOf(
                            currentUserId,
                            otherUserId
                        )
                    )
                )

                currentUserDocRef
                    .collection("engagedChatChannels")
                    .document(otherUserId)
                    .set(mapOf("channelId" to newChannel.id))

                firestoreInstance.collection("users").document(otherUserId)
                    .collection("engagedChatChannels")
                    .document(currentUserId)
                    .set(mapOf("channelId" to newChannel.id))

                onComplete(newChannel.id)
            }
    }

    fun addChatMessagesListener(channelId: String, context: Context,
                                onListen: (List<Item>) -> Unit): ListenerRegistration {
        return chatChannelsCollectionRef.document(channelId).collection("messages")
            .orderBy("time")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "ChatMessagesListener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                val items = mutableListOf<Item>()
                querySnapshot!!.documents.forEach {
                    if (it["type"] == MessageType.TEXT)
                        items.add(TextMessageItem(it.toObject(TextMessage::class.java)!!, context))
                    else
                        items.add(ImageMessageItem(it.toObject(ImageMessage::class.java)!!, context))
                    return@forEach
                }
                 onListen(items)


            }
    }

    fun addChatListener(channelId: String, context: Context,
                                onListen: (List<Item>) -> Unit): ListenerRegistration {
            return chatChannelsCollectionRef.document(channelId).collection("lastmessage").orderBy("time", Query.Direction.DESCENDING)
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    if (firebaseFirestoreException != null) {
                        Log.e("FIRESTORE", "ChatMessagesListener error.", firebaseFirestoreException)
                        return@addSnapshotListener
                    }

                    val items = mutableListOf<Item>()
                    querySnapshot!!.documents.forEach {
                        items.add(ChatItem(it.toObject(Chat::class.java)!!, context))
                    }
                    onListen(items)
                }

    }

    fun getChatChannel(onComplete: (channel: MutableList<String>) -> Unit) {
        val data : MutableList<String> = mutableListOf()
        currentUserDocRef.collection("engagedChatChannels").get()
            .addOnSuccessListener {
                for (doc in it){
                    data.add(doc.data["channelId"] as String)
                }
                onComplete(data)
                return@addOnSuccessListener
            }.addOnFailureListener {
                return@addOnFailureListener
            }
    }

    fun sendMessage(message: Message, channelId: String) {
        chatChannelsCollectionRef.document(channelId)
            .collection("messages")
            .add(message)

        chatChannelsCollectionRef.document(channelId)
            .collection("lastmessage").document(channelId)
            .set(message)
    }

    //region FCM
    fun getFCMRegistrationTokens(onComplete: (tokens: MutableList<String>) -> Unit) {
        currentUserDocRef.get().addOnSuccessListener {
            val user = it.toObject(Ustad::class.java)!!
            onComplete(user.registrationTokens)
        }
    }

    fun setFCMRegistrationTokens(registrationTokens: MutableList<String>) {
        currentUserDocRef.update(mapOf("registrationTokens" to registrationTokens))
    }
    //endregion FCM
}