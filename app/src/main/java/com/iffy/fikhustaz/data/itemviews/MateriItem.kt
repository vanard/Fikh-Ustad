package com.iffy.fikhustaz.data.itemviews

import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.materi.IslamData
import com.iffy.fikhustaz.util.DateFormat
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_materi.*

class MateriItem(val message: IslamData) : Item(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.tv_title_materi.text = message.title
        viewHolder.tv_desc_materi.text = message.description
        viewHolder.tv_day_materi.text = DateFormat.secondToDate(message.date)

        Picasso.get()
            .load("https://scontent.fcgk16-1.fna.fbcdn.net/v/t1.0-9/14264084_1329675737050247_1489589864657686165_n.jpg?_nc_cat=107&_nc_ht=scontent.fcgk16-1.fna&oh=b1422c78338fdd45fc3a2891a72b2497&oe=5D1AF67C")
            .placeholder(R.drawable.ic_image)
            .into(viewHolder.img_materi)
    }

    override fun getLayout() = R.layout.item_materi

}