package com.iffy.fikhustaz.views.activity.login

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log.w
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.iffy.fikhustaz.views.activity.register.RegisterActivity
import com.iffy.fikhustaz.views.activity.reset.ResetActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.*

class LoginActivity : AppCompatActivity() , LoginContract.View{

    private lateinit var presenter: LoginPresenter
    private var googleSignInClient : GoogleSignInClient? = null
    private var currentUser : FirebaseUser? = null
    private val RC_SIGN_IN = 101
    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null){
            finish()
            startActivity(intentFor<HomeActivity>().newTask().clearTask())
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this@LoginActivity, gso)
        presenter = LoginPresenter(this, this)
        text()

        btn_login_login.setOnClickListener { tryLogin() }

        tv_daftar_login.setOnClickListener { startActivity<RegisterActivity>() }

        tv_lupa_pass.setOnClickListener { startActivity<ResetActivity>() }
    }

    private fun tryLogin(){
        val mEmail = email_et_login.text.toString()
        val mPass = pass_et_login.text.toString()

        if (!presenter.verify(mEmail, mPass)){
            return
        }
        presenter.login(mEmail, mPass)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                presenter.googleLogin(account!!)
            } catch (e: ApiException) {
                w("MainActivity", "Google sign in failed", e)
            }
        }
    }

    private fun googleLogin(){
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun text(){
        val texuto = tv_daftar_login.text.toString()
        val spannable = SpannableString(texuto)
        val lengthTexuto = texuto.length - 6
        val text = StyleSpan(Typeface.BOLD)
        spannable.setSpan(
            text,
            lengthTexuto,
            texuto.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.green_app)),
            lengthTexuto,
            texuto.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tv_daftar_login.text = spannable
    }

    override fun showMsg(msg: String) {
        toast(msg)
    }

    override fun showLoading() {
        dialog = ProgressDialog.show(this@LoginActivity, "Login", "Logged In")
        dialog.setCancelable(false)
        dialog.isIndeterminate
    }

    override fun hideLoading() {
        dialog.dismiss()
    }
}
