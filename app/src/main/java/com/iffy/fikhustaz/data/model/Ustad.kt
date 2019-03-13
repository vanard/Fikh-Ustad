package com.iffy.fikhustaz.data.model

data class Ustad (val nama: String,
                  val email: String,
                  val handphone:String,
                  val profilePicture: String?,
                  val sertifikat: String?,
                  val ijazah: String?,
                  val registrationTokens: MutableList<String>){
    constructor(): this ("","","","","","",mutableListOf())
}