package com.iffy.fikhustaz.data.model.materi.konsulsyariah

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IsiFikh (val title: String, val author: String, val date: String, val category: String, val image: String, val description: String) : Parcelable {
    constructor(): this ("","","","","","")
}