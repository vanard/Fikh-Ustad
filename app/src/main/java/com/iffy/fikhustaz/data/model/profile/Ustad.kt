package com.iffy.fikhustaz.data.model.profile

import android.os.Parcelable
import com.iffy.fikhustaz.data.UserType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ustad (val nama: String?,
                  val email: String?,
                  val handphone:String?,
                  val tempatLahir: String?,
                  val tanggalLahir: String?,
                  val pendidikan: String?,
                  val keilmuan: String?,
                  val mazhab: String?,
                  val profilePicture: String?,
                  val sertifikat: String?,
                  val ijazah: String?,
                  val rate: Long?,
                  val schedule: MutableList<ItSchedule>?,
                  val type:String = UserType.USTAZ,
                  val userOnline: MutableList<ItOnline>?,
                  val registrationTokens: MutableList<String>) : Parcelable {
    constructor(): this ("","","","","","", "","","","","",100, mutableListOf(),UserType.USTAZ ,mutableListOf(),mutableListOf())
}