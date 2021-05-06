package com.iffy.fikhustaz.data.model.Notification

import com.google.gson.annotations.SerializedName

data class NotificationRoot(
    @SerializedName("to") val key : String,
    @SerializedName("notification") val body : Notification
) {
    constructor() : this("", Notification("", ""))
}