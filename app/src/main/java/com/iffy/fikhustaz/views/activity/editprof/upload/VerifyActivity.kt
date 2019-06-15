package com.iffy.fikhustaz.views.activity.editprof.upload

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.util.FirebaseUtil
import com.iffy.fikhustaz.views.activity.editprof.upload.DialogUploadFragment
import kotlinx.android.synthetic.main.activity_verify.*

class VerifyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        FirebaseUtil.getCurrentUser {
            if (it.ijazah != null && it.ijazah.isNotBlank())
                img_verify_ijazah.setImageDrawable(resources.getDrawable(R.drawable.ic_check_circle))
            if (it.sertifikat != null && it.sertifikat.isNotBlank())
                img_verify_certif.setImageDrawable(resources.getDrawable(R.drawable.ic_check_circle))
        }

        btnLayout_verify_ijazah.setOnClickListener {
            val i = DialogUploadFragment.newInstance("ijazah")
            i.show(supportFragmentManager,"ijazah")
        }

        btnLayout_verify_certif.setOnClickListener {
            val s = DialogUploadFragment.newInstance("sertifikat")
            s.show(supportFragmentManager,"sertif")
        }

    }
}
