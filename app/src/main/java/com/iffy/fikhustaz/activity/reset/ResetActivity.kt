package com.iffy.fikhustaz.activity.reset

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iffy.fikhustaz.R
import kotlinx.android.synthetic.main.activity_reset.*
import org.jetbrains.anko.toast

class ResetActivity : AppCompatActivity(), ResetContract.View {

    private lateinit var presenter:ResetPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)

        presenter = ResetPresenter(this)
        btn_reset_reset.setOnClickListener {
            val mail = et_email_reset.text.toString()
            if (!presenter.verify(mail)) return@setOnClickListener

            presenter.sendReset(mail)
        }
    }

    override fun showMsg(msg: String) {
        toast(msg)
    }
}
