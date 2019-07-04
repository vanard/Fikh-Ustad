package com.iffy.fikhustaz.data.itemviews

import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.materi.MateriUstad
import com.iffy.fikhustaz.views.activity.materi.MateriActivity
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_materi.view.*
import org.jetbrains.anko.startActivity

class MateriUstadItem (val materi: MateriUstad) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.tv_title_materi.text = materi.title
        viewHolder.itemView.tv_desc_materi.text = "[Materi Ustad]"
        viewHolder.itemView.tv_day_materi.text = ""

        Picasso.get()
            .load(materi.thumbnail)
            .placeholder(R.drawable.logo_app_fikh)
            .into(viewHolder.itemView.img_materi)

        viewHolder.itemView.setOnClickListener {
            viewHolder.itemView.context.startActivity<MateriActivity>("materi" to materi)
        }

    }

    override fun getLayout() = R.layout.item_materi
}