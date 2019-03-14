package com.iffy.fikhustaz.activity.register

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.activity.login.LoginActivity
import com.iffy.fikhustaz.data.model.Verify
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        presenter = RegisterPresenter(this)
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

        if (!presenter.verifyEntries(info)){
            return
        }
        presenter.saveData(info)
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
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }
}
