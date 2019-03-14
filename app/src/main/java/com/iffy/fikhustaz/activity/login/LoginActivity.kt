package com.iffy.fikhustaz.activity.login

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.activity.HomeActivity
import com.iffy.fikhustaz.activity.reset.ResetActivity
import com.iffy.fikhustaz.data.model.Verify
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() , LoginContract.View{

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this)
        text()

        btn_login_login.setOnClickListener {
            tryLogin()
        }

        tv_lupa_pass.setOnClickListener {
            startActivity<ResetActivity>()
        }
    }

    private fun tryLogin(){
        val mEmail = email_et_login.text.toString()
        val mPass = pass_et_login.text.toString()

        val info = Verify("", mEmail, "", mPass, "")

        if (!presenter.verify(info)){
            return
        }
        if (presenter.login(info)){
            startActivity<HomeActivity>()
        }
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
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }
}
