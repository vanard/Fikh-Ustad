package com.iffy.fikhustaz.views.activity.editprof

import android.net.Uri
import android.util.Log.d
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.iffy.fikhustaz.data.model.profile.Ustad
import com.iffy.fikhustaz.util.FirebaseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfPresenter(v: EditProfContract.View) : EditProfContract.Presenter {

    private val view = v
    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun getData() {
        view.showLoad()
        uiScope.launch {
            FirebaseUtil.getCurrentUser {
                view.setData(it)
                view.hideLoad()
            }
        }
    }

    override fun saveData(ustad: Ustad, selectedImageBytes: ByteArray?) {
        view.showLoad()
        if (ustad.profilePicture == null || selectedImageBytes == null){
            uiScope.launch {
                FirebaseUtil.updateCurrentUser(ustad.nama!!,ustad.email!!,ustad.handphone!!,ustad.tempatLahir!!,ustad.tanggalLahir!!,ustad.pendidikan!!,ustad.keilmuan!!,ustad.mazhab!!, "", "", "","",
                    mutableListOf())
                view.showMsg("Update Successfully")
            }
        }else{
            uiScope.launch {
                val user = FirebaseAuth.getInstance().currentUser

                val profileImageRef =
                    FirebaseStorage.getInstance().getReference("${user?.uid}/profilepics/${user?.uid}")


                profileImageRef.putBytes(selectedImageBytes).addOnCompleteListener {
                    profileImageRef.downloadUrl.addOnSuccessListener { uri ->

                        d("PTK", uri.toString())

                        if (uri != null) {
                            val profile = UserProfileChangeRequest.Builder()
                                .setPhotoUri(uri)
                                .build()

                            user!!.updateProfile(profile)

                            FirebaseUtil.updateCurrentUser(ustad.nama!!,ustad.email!!,ustad.handphone!!,ustad.tempatLahir!!,ustad.tanggalLahir!!,ustad.pendidikan!!,ustad.keilmuan!!,ustad.mazhab!!, uri.toString(), "", "","",
                                mutableListOf())
                            view.showMsg("Update Successfully")

                        }

                    }
                }

            }
        }
        view.hideLoad()
    }

}