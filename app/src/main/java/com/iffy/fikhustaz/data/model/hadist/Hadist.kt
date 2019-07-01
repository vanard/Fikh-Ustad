package com.iffy.fikhustaz.data.model.hadist

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hadist(val title: String, val title_link: String, val arab_hadist: String, val arti_hadist: String): Parcelable