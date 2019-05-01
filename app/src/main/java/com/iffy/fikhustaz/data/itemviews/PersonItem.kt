package com.iffy.fikhustaz.data.itemviews

import android.content.Context
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.profile.Ustad
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_person.*

class PersonItem(val person: Ustad,
                 val userId: String,
                 private val context: Context
)
    : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.tv_person_name.text = person.nama
        viewHolder.tv_last_message.text = person.handphone
    }

    override fun getLayout() = R.layout.item_person
}