package com.iffy.fikhustaz.data.itemviews

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.chat.Chat
import com.iffy.fikhustaz.data.model.chat.MessageType
import com.iffy.fikhustaz.util.DateFormat
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_person.*
import java.text.SimpleDateFormat

class ChatItem(val message: Chat,
               private val context: Context
)
    : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        if (message.senderId != FirebaseAuth.getInstance().currentUser!!.uid) {
            viewHolder.tv_person_name.text = message.senderName
        }else{
            viewHolder.tv_person_name.text = message.recipientName
        }
        if (message.type == MessageType.TEXT){
            viewHolder.tv_last_message.text = message.text
        }else{
            viewHolder.tv_last_message.text = "${message.senderName} sent a photo"
        }
        val dateFormat = SimpleDateFormat
            .getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT)
//        val a = DateFormat.reformatStringDate(dateFormat.format(message.time), "d/M/y H:m a", "HM:mm a")
        viewHolder.tv_last_date.text = dateFormat.format(message.time)

        Picasso.get()
    }

    override fun getLayout() = R.layout.item_person
}