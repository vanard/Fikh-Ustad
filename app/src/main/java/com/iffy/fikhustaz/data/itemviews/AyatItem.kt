package com.iffy.fikhustaz.data.itemviews

import android.text.Html
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.quran.Surat
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_ayat.view.*

class AyatItem(val msg: Surat) : Item(){

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.tv_ayat_arab.text = msg.ar
        viewHolder.itemView.tv_ayat.text = msg.id
        viewHolder.itemView.tv_arti_ayat.text = Html.fromHtml(msg.tr)
        viewHolder.itemView.tv_nomor_ayat.text = msg.nomor

    }

    override fun getLayout() = R.layout.item_ayat

}