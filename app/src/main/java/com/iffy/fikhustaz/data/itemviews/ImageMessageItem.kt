package com.iffy.fikhustaz.data.itemviews

import android.content.Context
import android.util.Log.d
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.chat.ImageMessage
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_image_message.*

class ImageMessageItem(val message: ImageMessage,
                       val context: Context
)
    : MessageItem(message) {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        super.bind(viewHolder, position)

        Picasso.get()
            .load(message.imagePath)
            .placeholder(R.drawable.ic_image)
            .into(viewHolder.image_message_image)
    }

    override fun getLayout() = R.layout.item_image_message

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        if (other !is ImageMessageItem)
            return false
        if (this.message != other.message)
            return false
        return true
    }

    override fun equals(other: Any?): Boolean {
        return isSameAs(other as? ImageMessageItem)
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + context.hashCode()
        return result
    }
}