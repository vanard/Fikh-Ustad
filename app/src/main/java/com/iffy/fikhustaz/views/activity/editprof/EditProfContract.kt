package com.iffy.fikhustaz.views.activity.editprof

import com.iffy.fikhustaz.data.model.profile.Ustad

interface EditProfContract {
    interface View{
        fun showLoad()
        fun hideLoad()
        fun showMsg(msg: String)
        fun showTimePicker(v: View)
        fun showDatePicker(v: View)
        fun setData(ustad: Ustad)
    }
    interface Presenter{
        fun getData()
        fun saveData(ustad: Ustad)
    }
}