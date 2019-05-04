package com.iffy.fikhustaz.data.model.quran

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Quran (val arti: String, val asma : String, val audio: String,
                  val ayat: String, val keterangan : String, val nama: String,
                  val nomor: String, val rukuk: String, val type: String, val urut: String) : Parcelable