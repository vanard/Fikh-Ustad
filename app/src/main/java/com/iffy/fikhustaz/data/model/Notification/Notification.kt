package com.iffy.fikhustaz.data.model.Notification

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
) {
    constructor() : this("", "");
}