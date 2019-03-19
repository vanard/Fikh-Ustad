package com.iffy.fikhustaz.data.model.chat

import java.util.*

data class Chat(val text: String,
                override val time: Date,
                override val senderId: String,
                override val recipientId: String,
                override val senderName: String,
                override val recipientName: String,
                override val type: String
)
    : Message {
    constructor() : this("", Date(0), "", "", "", "", "")
}