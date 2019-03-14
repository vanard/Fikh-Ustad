package com.iffy.fikhustaz.data.model

data class Verify (val name: String, val email: String, val phone: String, val pass: String, val conf: String){
    constructor(): this("", "", "", "", "")
}