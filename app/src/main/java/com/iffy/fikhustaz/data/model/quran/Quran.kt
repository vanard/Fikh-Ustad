package com.iffy.fikhustaz.data.model.quran

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Quran (val arti: String, val asma : String, val audio: String,
                  val ayat: String, val keterangan : String, val nama: String,
                  val nomor: String, val rukuk: String, val type: String, val urut: String) : Parcelable {

    companion object {
        const val TABLE_QURAN : String = "TABLE_QURAN"

        const val QURAN_ARTI: String = "QURAN_ARTI"
        const val QURAN_ASMA: String = "QURAN_ASMA"
        const val QURAN_AUDIO: String = "QURAN_AUDIO"
        const val QURAN_AYAT: String = "QURAN_AYAT"
        const val QURAN_KET: String = "QURAN_KETERANGAN"
        const val QURAN_NAMA: String = "QURAN_NAMA"
        const val QURAN_NOMOR: String = "QURAN_NOMOR"
        const val QURAN_RUKUK: String = "QURAN_RUKUK"
        const val QURAN_TIPE: String = "QURAN_TIPE"
        const val QURAN_URUT: String = "QURAN_URUT"

    }

}

