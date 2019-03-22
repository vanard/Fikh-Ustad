package com.iffy.fikhustaz.views.activity.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface LoginContract{
    interface View{
        fun showMsg(msg: String)
        fun showLoading()
        fun hideLoading()
    }
    interface Presenter{
        fun verify(email: String, pass: String) : Boolean
        fun login(email: String, pass: String)
        fun googleLogin(account: GoogleSignInAccount)
    }
}