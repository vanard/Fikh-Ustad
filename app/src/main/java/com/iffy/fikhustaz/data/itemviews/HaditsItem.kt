package com.iffy.fikhustaz.data.itemviews

import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.hadist.Hadist
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_hadits.view.*

class HaditsItem(val hadist: Hadist) : Item(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tv_hadits_title.text = hadist.title
        viewHolder.itemView.tv_hadits_arab_text.text =  hadist.arab_hadist
        viewHolder.itemView.tv_hadits_text.text = hadist.arti_hadist
    }

    override fun getLayout() = R.layout.item_hadits

}