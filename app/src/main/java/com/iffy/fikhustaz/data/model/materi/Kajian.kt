package com.iffy.fikhustaz.data.model.materi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Kajian(@SerializedName("links") val links: IslamLink,
                    @SerializedName("data") val data: MutableList<IslamData>) : Parcelable