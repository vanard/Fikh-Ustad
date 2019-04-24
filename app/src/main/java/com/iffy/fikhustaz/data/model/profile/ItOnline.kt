package com.iffy.fikhustaz.data.model.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItOnline(val status: String, val time: Long) : Parcelable {
    constructor() : this("", 0)
}