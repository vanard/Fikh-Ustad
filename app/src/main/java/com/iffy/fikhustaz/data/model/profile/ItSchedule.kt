package com.iffy.fikhustaz.data.model.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItSchedule(val day: String, val timeStart: String, val timeEnd: String) : Parcelable {
    constructor() : this("", "", "")
}