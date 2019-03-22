package com.iffy.fikhustaz.views.activity.register

import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.iid.FirebaseInstanceId
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.iffy.fikhustaz.data.model.Ustad
import com.iffy.fikhustaz.data.model.Verify
import com.iffy.fikhustaz.service.MyFirebaseInstanceIDService
import com.iffy.fikhustaz.util.FirebaseUtil

class RegisterPresenter (v: RegisterContract.View, ctx: Context) : RegisterContract.Presenter {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var view: RegisterContract.View? = v
    private val mCtx = ctx


    override fun verifyEntries(data: Verify) : Boolean{
        if (data.name.isEmpty()){
            view?.showMsg("Nama harus diisi")
            return false
        }
        if (data.name.length < 3){
            view?.showMsg("Nama minimal 3 karakter")
            return false
        }
        if (data.email.isEmpty()){
            view?.showMsg("Email harus diisi")
            return false
        }
        if (data.phone.isEmpty()){
            view?.showMsg("Nomor harus diisi")
            return false
        }
        if (data.phone.length < 10){
            view?.showMsg("Nomor minimal 10 digit")
            return false
        }
        if (data.pass.isEmpty()){
            view?.showMsg("Password harus diisi")
            return false
        }
        if (data.pass.length < 6){
            view?.showMsg("Password minimal 6 karakter")
            return false
        }
        if (data.pass != data.conf){
            view?.showMsg("Password tidak sama")
            return false
        }

        return true

    }

    override fun saveData(data: Ustad, pass: String) {
        view?.showLoading()
        mAuth.createUserWithEmailAndPassword(data.email!!, pass).addOnSuccessListener {
            FirebaseUtil.initCurrentUserIfFirstTime(data){
                    val user = mAuth.currentUser
                    if (user != null){
                        val prof : UserProfileChangeRequest = UserProfileChangeRequest.Builder()
                            .setDisplayName(data.nama)
                            .build()

                        user.updateProfile(prof).addOnSuccessListener {
                            val registrationToken = FirebaseInstanceId.getInstance().token
                            MyFirebaseInstanceIDService.addTokenToFirestore(registrationToken)

                            view?.hideLoading()
                            view?.showMsg("Success")

                            val intent = Intent(mCtx, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            mCtx.startActivity(intent)
                        }

                    }

                }
            }.addOnFailureListener {
                view?.showMsg(it.localizedMessage)
                view?.hideLoading()
            }

    }

}