package com.iffy.fikhustaz.activity.login

import com.iffy.fikhustaz.data.model.Verify

interface LoginContract{
    interface View{
        fun showMsg(msg: String)
        fun showLoading()
        fun hideLoading()
    }
    interface Presenter{
        fun verify(data: Verify) : Boolean
        fun login(data: Verify): Boolean
    }
}