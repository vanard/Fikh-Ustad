package com.iffy.fikhustaz.views.activity.editprof.upload

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.StatusAccount
import com.iffy.fikhustaz.util.FirebaseUtil
import kotlinx.android.synthetic.main.activity_verify.*

class VerifyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        FirebaseUtil.getCurrentUser {
            if (it.ijazah != null && it.ijazah.isNotBlank())
            {
                if(it.verified == StatusAccount.VERIFIED)
                    img_verify_ijazah.setImageResource(R.drawable.ic_check_circle)
                if (it.verified == StatusAccount.PENDING)
                    img_verify_ijazah.setImageResource(R.drawable.ic_exclamation_mark)

            }
            if (it.sertifikat != null && it.sertifikat.isNotBlank()) {
                if (it.verified == StatusAccount.VERIFIED)
                    img_verify_certif.setImageResource(R.drawable.ic_check_circle)
                if (it.verified == StatusAccount.PENDING)
                    img_verify_certif.setImageResource(R.drawable.ic_exclamation_mark)

            }
            if (it.profilePicture != null && it.profilePicture.isNotBlank()) {
                if (it.verified == StatusAccount.VERIFIED)
                    img_verify_certif.setImageResource(R.drawable.ic_check_circle)
                if (it.verified == StatusAccount.PENDING)
                    img_verify_certif.setImageResource(R.drawable.ic_exclamation_mark)

            }

        }

        btnLayout_verify_ijazah.setOnClickListener {
            val i = DialogUploadFragment.newInstance("ijazah")
            i.show(supportFragmentManager,"ijazah")
        }

        btnLayout_verify_certif.setOnClickListener {
            val s = DialogUploadFragment.newInstance("sertifikat")
            s.show(supportFragmentManager,"sertif")
        }

        btnLayout_verify_profilepic.setOnClickListener {
            val p = DialogUploadFragment.newInstance("profilePicture")
            p.show(supportFragmentManager, "profpic")
        }

    }
}
