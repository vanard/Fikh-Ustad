package com.iffy.fikhustaz.data.itemviews

import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.views.activity.materi.MateriActivity
import com.iffy.fikhustaz.data.model.materi.IslamData
import com.iffy.fikhustaz.util.DatesFormat
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_materi.view.*
import org.jetbrains.anko.startActivity

class MateriItem(val message: IslamData) : Item(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tv_title_materi.text = message.title
        viewHolder.itemView.tv_desc_materi.text = message.description
        viewHolder.itemView.tv_day_materi.text = DatesFormat.secondToDay(message.date)

        Picasso.get()
            .load(R.drawable.logo_islam_house)
            .placeholder(R.drawable.ic_image)
            .into(viewHolder.itemView.img_materi)

        viewHolder.itemView.setOnClickListener {
            viewHolder.itemView.context.startActivity<MateriActivity>("materi" to message.attach)
        }

    }

    override fun getLayout() = R.layout.item_materi

}