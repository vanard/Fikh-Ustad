package com.iffy.fikhustaz.fragment.pesan

interface PesanContract{
    interface View{
        fun showLoad()
        fun hideLoad()
        fun showMsg(msg: String)
        fun fillData(data: MutableList<String>)
    }
    interface Presenter{
        fun getLastMessage()
    }
}