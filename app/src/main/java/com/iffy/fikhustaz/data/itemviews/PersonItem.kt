package com.iffy.fikhustaz.data.itemviews

import android.content.Context
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.Ustad
import com.iffy.fikhustaz.util.StorageUtil
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_person.*
import java.io.File

class PersonItem(val person: Ustad,
                 val userId: String,
                 private val context: Context
)
    : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView_name.text = person.nama
        viewHolder.textView_bio.text = person.handphone
        if (person.profilePicture != null)
            Picasso.get()
                .load(File(StorageUtil.pathToReference(person.profilePicture).toString()))
                .placeholder(R.drawable.ic_account_circle)
                .into(viewHolder.imageView_profile_picture)
    }

    override fun getLayout() = R.layout.item_person
}