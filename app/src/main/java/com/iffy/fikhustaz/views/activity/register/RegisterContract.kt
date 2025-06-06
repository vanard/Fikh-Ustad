package com.iffy.fikhustaz.views.activity.register

import com.iffy.fikhustaz.data.model.profile.Ustad
import com.iffy.fikhustaz.data.model.Verify

interface RegisterContract {
    interface View {
        fun showMsg(msg: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun verifyEntries(data: Verify): Boolean
        fun saveData(data: Ustad, pass: String)
    }
}