package com.iffy.fikhustaz.data.model.quran

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Surat (val idQuran:String?, val ar: String, val id: String, val nomor: String, val tr: String) : Parcelable {

    companion object {
        const val TABLE_SURAH : String = "TABLE_SURAH"
        const val ID : String = "QURAN_ID"

        const val SURAH_AR: String = "SURAH_ARAB"
        const val SURAH_ID: String = "SURAH_INDO"
        const val SURAH_NOMOR: String = "SURAH_NOMOR"
        const val SURAH_TR: String = "SURAH_TRANSLATE"

    }

}