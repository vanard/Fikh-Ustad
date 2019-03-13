package com.iffy.fikhustaz.data.model

data class ChatChannel(val userIds: MutableList<String>) {
    constructor() : this(mutableListOf())
}