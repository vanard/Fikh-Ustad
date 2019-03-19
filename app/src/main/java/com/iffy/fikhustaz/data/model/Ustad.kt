package com.iffy.fikhustaz.data.model

data class Ustad (val nama: String?,
                  val email: String?,
                  val handphone:String?,
                  val tempatLahir: String?,
                  val tanggalLahir: String?,
                  val pendidikan: String?,
                  val keilmuan: String?,
                  val firkah: String?,
                  val mazhab: String?,
                  val profilePicture: String?,
                  val sertifikat: String?,
                  val ijazah: String?,
                  val rate: String?,
                  val userOnline: MutableList<String>,
                  val registrationTokens: MutableList<String>){
    constructor(): this ("","","","","","", "","","","","","", "",mutableListOf(),mutableListOf())
}