package com.iffy.fikhustaz.data.model.chat

import com.iffy.fikhustaz.data.MessageType
import java.util.*

data class ImageMessage(val imagePath: String,
                        override val time: Date,
                        override val senderId: String,
                        override val recipientId: String,
                        override val senderName: String,
                        override val recipientName: String,
                        override val type: String = MessageType.IMAGE
)
    : Message {
    constructor() : this("", Date(0), "", "", "", "")
}