package com.iffy.fikhustaz.data.model.hadist

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KitabHadist(val kitab : String, val kitab_link : String, val code_kitab : String, val jumlah : String) : Parcelable