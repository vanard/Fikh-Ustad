package com.iffy.fikhustaz.data.itemviews

import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.materi.konsulsyariah.IsiFikh
import com.iffy.fikhustaz.views.activity.detailmateri.DetailMateriActivity
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_materi.view.*
import org.jetbrains.anko.startActivity

class MateriFikhItem(val fikh: IsiFikh) : Item(){

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.tv_title_materi.text = fikh.title
        viewHolder.itemView.tv_desc_materi.text = fikh.category.split("\n").toString()
        viewHolder.itemView.tv_day_materi.text = ""

        if (fikh.image.isEmpty() || fikh.image.isBlank()) {
            Picasso.get()
                .load(R.drawable.logo_app_fikh)
                .placeholder(R.drawable.logo_app_fikh)
                .into(viewHolder.itemView.img_materi)
        } else {
            Picasso.get()
                .load(fikh.image)
                .placeholder(R.drawable.logo_app_fikh)
                .into(viewHolder.itemView.img_materi)
        }

        viewHolder.itemView.setOnClickListener {
            viewHolder.itemView.context.startActivity<DetailMateriActivity>("data" to fikh)
        }

    }

    override fun getLayout() = R.layout.item_materi

}