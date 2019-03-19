package com.iffy.fikhustaz.activity.login

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.iid.FirebaseInstanceId
import com.iffy.fikhustaz.activity.HomeActivity
import com.iffy.fikhustaz.data.model.Ustad
import com.iffy.fikhustaz.service.MyFirebaseInstanceIDService
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
                mAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener {
                    view?.showMsg("Login Success")
                    view?.hideLoading()
                    val intent = Intent(mCtx, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mCtx.startActivity(intent)
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
                    val data = Ustad(user.displayName, user.email, user.phoneNumber, "","", "","","","",user.photoUrl.toString(),"","","", mutableListOf())
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