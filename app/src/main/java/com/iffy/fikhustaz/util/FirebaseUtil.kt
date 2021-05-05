package com.iffy.fikhustaz.util

import android.content.Context
import android.net.Uri
import android.util.Log
import android.util.Log.d
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import com.iffy.fikhustaz.data.MessageType
import com.iffy.fikhustaz.data.itemviews.ChatItem
import com.iffy.fikhustaz.data.itemviews.ImageMessageItem
import com.iffy.fikhustaz.data.itemviews.PersonItem
import com.iffy.fikhustaz.data.itemviews.TextMessageItem
import com.iffy.fikhustaz.data.model.chat.*
import com.iffy.fikhustaz.data.model.materi.MateriUstad
import com.iffy.fikhustaz.data.model.profile.ItOnline
import com.iffy.fikhustaz.data.model.profile.ItSchedule
import com.iffy.fikhustaz.data.model.profile.Ustad
import com.xwray.groupie.kotlinandroidextensions.Item

object FirebaseUtil {
    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().currentUser?.uid
            ?: throw NullPointerException("UID is null.")}")

    private val chatChannelsCollectionRef = firestoreInstance.collection("chatChannels")
    private val materiCollectionRef = firestoreInstance.collection("materi")
    private val user = FirebaseAuth.getInstance().currentUser

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
                          mazhab: String = "",
                          profilePicture: String? = null,
                          sertifikat: String? = null,
                          ijazah: String? = null,
                          rate: Long? = null,
                          verified: String? = null,
                          userOnline : List<ItOnline>? = null,
                          schedule: List<ItSchedule>? = null
    ) {
        val userFieldMap = mutableMapOf<String, Any>()
        if (nama.isNotBlank()) userFieldMap["nama"] = nama
        if (email.isNotBlank()) userFieldMap["email"] = email
        if (handphone.isNotBlank()) userFieldMap["handphone"] = handphone
        if (tempatLahir.isNotBlank()) userFieldMap["tempatLahir"] = tempatLahir
        if (tanggalLahir.isNotBlank()) userFieldMap["tanggalLahir"] = tanggalLahir
        if (pendidikan.isNotBlank()) userFieldMap["pendidikan"] = pendidikan
        if (keilmuan.isNotBlank()) userFieldMap["keilmuan"] = keilmuan
        if (mazhab.isNotBlank()) userFieldMap["mazhab"] = mazhab
        if (profilePicture != null && profilePicture.isNotBlank())
            userFieldMap["profilePicture"] = profilePicture
        if (sertifikat != null && sertifikat.isNotBlank())
            userFieldMap["sertifikat"] = sertifikat
        if (ijazah != null && ijazah.isNotBlank())
            userFieldMap["ijazah"] = ijazah
        if (verified != null && verified.isNotBlank())
            userFieldMap["verified"] = verified
        if (schedule != null)
            userFieldMap["schedule"] = schedule
        if (userOnline != null)
            userFieldMap["userOnline"] = userOnline

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

    fun getLastMessageListener(channelId: String, onListen: () -> Unit): ListenerRegistration{
        return chatChannelsCollectionRef.document(channelId).collection("lastmessage")
            .addSnapshotListener { _, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "ChatMessagesListener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

//                for (dc in querySnapshot!!.documentChanges) {
//                    when (dc.type) {
//                        DocumentChange.Type.ADDED -> ChatItem(querySnapshot.documents[0].toObject(Chat::class.java)!!, context)
//                        DocumentChange.Type.MODIFIED -> {
//                            ChatItem(querySnapshot.documents[0].toObject(Chat::class.java)!!, context)
//                        }
//                        DocumentChange.Type.REMOVED -> Log.d("FirebaseUtil", "Removed city: ${dc.document.data}")
//                    }
//                }

                onListen()
            }

    }

    fun getLastMessage(channelId: List<String>, onComplete: (List<ChatItem>) -> Unit){
        val mList : MutableList<ChatItem> = mutableListOf()
        channelId.forEach {channel ->
            chatChannelsCollectionRef.document(channel).collection("lastmessage").orderBy("time").get().addOnSuccessListener {
                it!!.documents.forEach {doc ->
                    mList.add(ChatItem(doc.toObject(Chat::class.java)!!))
                }
                onComplete(mList)
            }

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
                d("error", it.localizedMessage)
                return@addOnFailureListener
            }
    }

    fun sendMessage(
        message: Message,
        channelId: String,
        profilePicture: String
    ) {
        chatChannelsCollectionRef.document(channelId)
            .collection("messages")
            .add(message)

        chatChannelsCollectionRef.document(channelId)
            .collection("lastmessage").document(channelId)
            .set(message)

        val userFieldMap = mutableMapOf<String, Any>()
        userFieldMap["profilePic"] = profilePicture

        chatChannelsCollectionRef.document(channelId)
            .collection("lastmessage").document(channelId).update(userFieldMap)
    }

    fun putProfileBytes(uid: String?, image: ByteArray, mNama: String?, onComplete: (uri: Uri) -> Unit){
        val profileImageRef =
            FirebaseStorage.getInstance().getReference("$uid/profilePicture/$uid")

        profileImageRef.putBytes(image).addOnCompleteListener {
            profileImageRef.downloadUrl.addOnSuccessListener { uri ->

                if (uri != null) {
                    val profile = UserProfileChangeRequest.Builder()
                        .setPhotoUri(uri)
                        .setDisplayName(mNama)
                        .build()

                    user!!.updateProfile(profile).addOnCompleteListener {
                        if (it.isSuccessful) {
                            onComplete(uri)
                        }
                    }
                }
            }
        }
    }

    fun putMateriFile(uid: String?, title: String, mFile: Uri, thumb: ByteArray, onComplete: () -> Unit){
        val fileRef =
            FirebaseStorage.getInstance().getReference("$uid/materi/$title.pdf")

        val thumbnailMateriRef =
            FirebaseStorage.getInstance().getReference("$uid/thumbnail/$title")

        fileRef.putFile(mFile).addOnCompleteListener {
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                thumbnailMateriRef.putBytes(thumb).addOnCompleteListener {
                    thumbnailMateriRef.downloadUrl.addOnSuccessListener { ur ->
                        materiCollectionRef.add(MateriUstad(uid!!, user?.displayName!!, title, ur.toString(), uri.toString())).addOnSuccessListener {
                            onComplete()
                        }
                    }
                }
            }
        }
    }

    fun updateStatusOnline(status: String){

        val onlineData = mutableListOf<ItOnline>()
        if(status == "online") {
            onlineData.add(
                0, ItOnline(
                    "Online",
                    0
                )
            )
        }
        currentUserDocRef.update("userOnline", onlineData)
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