package com.iffy.fikhustaz.views.activity.login

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.iid.FirebaseInstanceId
import com.iffy.fikhustaz.data.UserType
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.iffy.fikhustaz.data.model.profile.Ustad
import com.iffy.fikhustaz.service.MyFirebaseInstanceIDService
import com.iffy.fikhustaz.service.MyFirebaseMessagingService
import com.iffy.fikhustaz.util.FirebaseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginPresenter (v: LoginContract.View, ctx: Context) : LoginContract.Presenter{
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var view: LoginContract.View? = v
    private val mCtx = ctx

    override fun verify(email: String, pass: String): Boolean {
        if (email.isEmpty()){
            view?.showMsg("Username harus diisi")
            return false
        }
        if (pass.isEmpty()){
            view?.showMsg("Password harus diisi")
            return false
        }

        return true

    }

    override fun login(email: String, pass: String) {
        view?.showLoading()
        val uiScope = CoroutineScope(Dispatchers.Main)
        uiScope.launch {
            try {
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(mCtx, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

                        view?.showMsg("Login Success")
                        view?.hideLoading()

                        mCtx.startActivity(intent)
                    }else{
                        if (it.exception != null){
                            view?.showMsg(it.exception!!.localizedMessage)
                        }
                        view?.hideLoading()

                    }

                }.addOnFailureListener {
                    view?.showMsg(it.localizedMessage)
                    view?.hideLoading()
                }
                view?.hideLoading()
            } catch (e: Throwable) {
                view?.showMsg("Ooops: Something else went wrong")
            }
        }
    }

    override fun googleLogin(account: GoogleSignInAccount) {
        view?.showLoading()
        val mCredential = GoogleAuthProvider.getCredential(account.idToken, null)
        mAuth.signInWithCredential(mCredential).addOnCompleteListener {
            if (it.isSuccessful){
                val user = mAuth.currentUser
                if (user != null){
                    val data = Ustad(
                        user.displayName,
                        user.email,
                        user.phoneNumber,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        user.photoUrl.toString(),
                        "",
                        100,
                        mutableListOf(),
                        UserType.USTAZ,
                        mutableListOf(),
                        mutableListOf()
                    )
                    FirebaseUtil.initCurrentUserIfFirstTime(data){
                        val registrationToken = FirebaseInstanceId.getInstance().token
                        MyFirebaseInstanceIDService.addTokenToFirestore(registrationToken)

                        val intent = Intent(mCtx, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        mCtx.startActivity(intent)

                        view?.hideLoading()


                    }
                }

            }else{
                view?.hideLoading()
            }
        }
    }


}