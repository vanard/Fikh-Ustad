package com.iffy.fikhustaz.activity.reset

import com.google.firebase.auth.FirebaseAuth

class ResetPresenter (v: ResetContract.View) : ResetContract.Presenter{
    private val view = v

    override fun verify(input: String): Boolean {
        if (input.isEmpty()) {
            view.showMsg("Email harus diisi")
            return false
        }
        return true
    }

    override fun sendReset(email: String) {
        val auth = FirebaseAuth.getInstance()

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    view.showMsg( "Send Reset Password Email")
                }else{
                    view.showMsg( "Failed to Send Reset Password Email")
                }
            }
    }

}