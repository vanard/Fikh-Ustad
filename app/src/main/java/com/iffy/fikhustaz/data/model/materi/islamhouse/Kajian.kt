package com.iffy.fikhustaz.data.model.materi.islamhouse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Kajian(@SerializedName("data") val data: MutableList<IslamData>,
                  @SerializedName("links") val links: IslamLink
) : Parcelable