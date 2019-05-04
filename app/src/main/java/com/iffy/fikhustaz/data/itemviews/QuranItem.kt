package com.iffy.fikhustaz.data.itemviews

import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.quran.Quran
import com.iffy.fikhustaz.views.activity.quran.SurahActivity
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_quran.view.*
import org.jetbrains.anko.startActivity

class QuranItem(val msg: Quran) : Item(){

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.quran_tv_number.text = msg.nomor
        viewHolder.itemView.quran_tv_name.text = msg.nama
        viewHolder.itemView.quran_tv_type.text = "${msg.type} | "
        viewHolder.itemView.quran_tv_ayat.text = "${msg.ayat} Ayat"
        viewHolder.itemView.quran_tv_arab.text = msg.asma

        viewHolder.itemView.setOnClickListener {
            viewHolder.itemView.context.startActivity<SurahActivity>("quran" to msg)
        }

    }

    override fun getLayout() = R.layout.item_quran

}