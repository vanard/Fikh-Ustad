package com.iffy.fikhustaz.data.model.materi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IslamData (@SerializedName("id") val id:String,
                      @SerializedName("source_id") val source_id:String,
                      @SerializedName("title") val title: String,
                      @SerializedName("add_date") val date: Long,
                      @SerializedName("description")val description: String,
                      @SerializedName("attachments") val attach:MutableList<Attachment>) : Parcelable