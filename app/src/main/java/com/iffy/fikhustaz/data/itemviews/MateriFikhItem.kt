package com.iffy.fikhustaz.data.itemviews

import android.util.Log.d
import com.bumptech.glide.Glide
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.materi.konsulsyariah.Fikih
import com.iffy.fikhustaz.data.model.materi.konsulsyariah.IsiFikh
import com.iffy.fikhustaz.views.activity.detailmateri.DetailMateriActivity
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_materi.view.*
import org.jetbrains.anko.startActivity

class MateriFikhItem(val message: Fikih, val fikh: IsiFikh) : Item(){

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.tv_title_materi.text = message.title
        viewHolder.itemView.tv_desc_materi.text = fikh.category.split("\n").toString()
        viewHolder.itemView.tv_day_materi.text = ""

        Picasso.get()
            .load(fikh.image)
            .placeholder(R.drawable.logo_app_fikh)
            .into(viewHolder.itemView.img_materi)

//        Glide.with(viewHolder.containerView).load(fikh.image).placeholder(R.drawable.logo_app_fikh).into(viewHolder.itemView.img_materi)

        viewHolder.itemView.setOnClickListener {
            viewHolder.itemView.context.startActivity<DetailMateriActivity>("data" to fikh)
        }

    }

    override fun getLayout() = R.layout.item_materi

}