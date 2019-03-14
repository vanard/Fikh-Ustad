package com.iffy.fikhustaz.data.model.chat

data class ChatChannel(val userIds: MutableList<String>) {
    constructor() : this(mutableListOf())
}