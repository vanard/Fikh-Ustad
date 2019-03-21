package com.iffy.fikhustaz.activity.chat

interface ChatContract{
    interface View{
        fun showData(channelId:String)
    }
    interface Presenter{
        fun getData(id: String)
    }
}