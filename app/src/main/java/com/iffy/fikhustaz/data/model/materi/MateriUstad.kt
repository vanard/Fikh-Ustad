package com.iffy.fikhustaz.data.model.materi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MateriUstad(val uid: String, val name: String, val title: String, val thumbnail: String, val file: String) : Parcelable {
    constructor() : this("", "", "", "", "")
}