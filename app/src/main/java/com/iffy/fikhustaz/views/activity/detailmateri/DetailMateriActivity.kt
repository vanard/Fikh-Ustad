package com.iffy.fikhustaz.views.activity.detailmateri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.materi.konsulsyariah.IsiFikh
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_materi.*

class DetailMateriActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_materi)

        if (intent != null) {
            val a = intent.getParcelableExtra<IsiFikh>("data")
            if (a != null){
                Picasso.get().load(a.image).placeholder(R.drawable.logo_app_fikh).into(img_detailmateri)
                tv_author_detailmateri.text = a.author
                tv_date_detailmateri.text = a.date
                tv_description_detailmateri.text = a.description
            }
        }
    }
}
