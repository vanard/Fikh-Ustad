package com.iffy.fikhustaz.data.model.chat

import java.util.*

interface Message {
    val time: Date
    val senderId: String
    val recipientId: String
    val senderName: String
    val recipientName: String
    val type: String
}