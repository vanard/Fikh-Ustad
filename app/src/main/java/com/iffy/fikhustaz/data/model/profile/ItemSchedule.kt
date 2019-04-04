package com.iffy.fikhustaz.data.model.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemSchedule(val day: String, val time: String) : Parcelable {
    constructor() : this("", "")
}