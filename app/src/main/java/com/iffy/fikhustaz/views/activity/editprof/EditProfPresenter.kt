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
    private var mUstad: Ustad? = null

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
        val user = FirebaseAuth.getInstance().currentUser
        mUstad = ustad

        if (ustad.profilePicture == null || selectedImageBytes == null){
            uiScope.launch {
                FirebaseUtil.updateCurrentUser(ustad.nama!!,ustad.email!!,ustad.handphone!!,ustad.tempatLahir!!,
                    ustad.tanggalLahir!!,ustad.pendidikan!!,ustad.keilmuan!!,ustad.mazhab!!, "",
                    "", "",null, null, ustad.userOnline, ustad.schedule)

                val profile = UserProfileChangeRequest.Builder()
                    .setDisplayName(ustad.nama)
                    .build()

                user!!.updateProfile(profile).addOnCompleteListener {
                    if(it.isSuccessful) {
                        view.showMsg("Update Successfully")

                        view.hideLoad()
                        view.onSuccess()
                    }
                }

            }

        }else{
            uiScope.launch {
                FirebaseUtil.putProfileBytes(user?.uid, selectedImageBytes, ustad.nama, this@EditProfPresenter::updateUser)
            }
        }
    }

    private fun updateUser(uri: Uri){
        FirebaseUtil.updateCurrentUser(mUstad?.nama!!,mUstad?.email!!,mUstad?.handphone!!,mUstad?.tempatLahir!!,
            mUstad?.tanggalLahir!!,mUstad?.pendidikan!!,mUstad?.keilmuan!!,mUstad?.mazhab!!, uri.toString(),
            "", "",null, null,mUstad?.userOnline, mUstad?.schedule)

        view.showMsg("Update Successfully")
        view.hideLoad()
        view.onSuccess()
    }

}