package com.iffy.fikhustaz.activity.reset

interface ResetContract{
    interface View{
        fun showMsg(msg: String)
    }
    interface Presenter{
        fun verify(input: String): Boolean
        fun sendReset(email: String)
    }
}