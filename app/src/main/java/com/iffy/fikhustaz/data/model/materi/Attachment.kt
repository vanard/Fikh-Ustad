package com.iffy.fikhustaz.data.model.materi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Attachment (@SerializedName("order") val order:String,
                       @SerializedName("description") val title: String,
                       @SerializedName("url") val url: String) : Parcelable