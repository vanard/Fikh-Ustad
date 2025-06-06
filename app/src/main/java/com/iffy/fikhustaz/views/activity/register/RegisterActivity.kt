package com.iffy.fikhustaz.views.activity.register

import android.app.ProgressDialog
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.StatusAccount
import com.iffy.fikhustaz.data.UserType
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.iffy.fikhustaz.views.activity.login.LoginActivity
import com.iffy.fikhustaz.data.model.profile.Ustad
import com.iffy.fikhustaz.data.model.Verify
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.*

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var presenter: RegisterPresenter
    private var currentUser : FirebaseUser? = null
    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser

        if (currentUser != null){
            finish()
            startActivity(intentFor<HomeActivity>().newTask().clearTask())
        }

        presenter = RegisterPresenter(this, this)
        text()

        btn_regist_register.setOnClickListener {
            putin()
        }

        tv_login_register.setOnClickListener {
            startActivity<LoginActivity>()
        }
    }

    private fun putin(){
        val mName = name_et_register.text.toString()
        val mEmail = email_et_register.text.toString()
        val mPhone = phone_et_register.text.toString()
        val mPass = pass_et_register.text.toString()
        val mConfpass = confpass_et_register.text.toString()

        val info = Verify(mName, mEmail, mPhone, mPass, mConfpass)
        val newUser = Ustad(
            mName,
            mEmail,
            mPhone,
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            100,
            StatusAccount.UNVERIFIED,
            mutableListOf(),
            UserType.USTAZ,
            mutableListOf(),
            mutableListOf()
        )

        if (!presenter.verifyEntries(info)){
            return
        }
        presenter.saveData(newUser, mPass)
    }

    private fun text(){
        val texuto = tv_login_register.text.toString()
        val spannable = SpannableString(texuto)
        val lengthTexuto = texuto.length - 5
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

        tv_login_register.text = spannable
    }

    override fun showMsg(msg: String) {
        toast(msg)
    }

    override fun showLoading() {
        dialog = ProgressDialog.show(this@RegisterActivity, "Register", "Preparing profile user")
        dialog.setCancelable(false)
        dialog.isIndeterminate
    }

    override fun hideLoading() {
        dialog.dismiss()
    }
}
