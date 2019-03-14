package com.iffy.fikhustaz.data.model.materi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IslamLink (@SerializedName("next") val next: String,
                      @SerializedName("prev") val prev: String,
                      @SerializedName("current_page") val cur_page: String) : Parcelable