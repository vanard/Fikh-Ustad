package com.iffy.fikhustaz.data.model.materi.konsulsyariah

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fikih (
    val title: String,
    val link: String,
    val dateTime: String,
    val date: String,
    val choose: String,
    val thumb: String
) : Parcelable {
    constructor(): this ("","","","","", "")
}