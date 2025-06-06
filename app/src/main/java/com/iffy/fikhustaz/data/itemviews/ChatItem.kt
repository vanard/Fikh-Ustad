package com.iffy.fikhustaz.data.itemviews

import com.google.firebase.auth.FirebaseAuth
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.MessageType
import com.iffy.fikhustaz.data.model.chat.Chat
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_person.*
import java.text.SimpleDateFormat

class ChatItem(val message: Chat
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
            if (message.senderId == FirebaseAuth.getInstance().currentUser!!.uid){
                viewHolder.tv_last_message.text = "You sent a photo"
            }
            viewHolder.tv_last_message.text = "${message.senderName} sent a photo"
        }
        val dateFormat = SimpleDateFormat
            .getTimeInstance(SimpleDateFormat.SHORT)
        viewHolder.tv_last_date.text = dateFormat.format(message.time)

        Picasso.get()
    }

    override fun getLayout() = R.layout.item_person
}