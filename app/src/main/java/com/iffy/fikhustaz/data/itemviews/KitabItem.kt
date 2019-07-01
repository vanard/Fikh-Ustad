package com.iffy.fikhustaz.data.itemviews

import android.view.View
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.hadist.KitabHadist
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_person.view.*

class KitabItem(val hadist: KitabHadist) : Item(){

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.tv_person_name.text = hadist.kitab
        viewHolder.itemView.tv_last_message.text = hadist.jumlah
        viewHolder.itemView.tv_last_date.visibility = View.GONE

    }

    override fun getLayout() = R.layout.item_person

}