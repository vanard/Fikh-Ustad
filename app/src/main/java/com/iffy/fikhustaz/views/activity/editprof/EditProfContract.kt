package com.iffy.fikhustaz.views.activity.editprof

import com.iffy.fikhustaz.data.model.Ustad

interface EditProfContract {
    interface View{
        fun showLoad()
        fun hideLoad()
        fun showTimePicker(v: View)
        fun showDatePicker(v: View)
        fun setData(ustad: Ustad)
    }
    interface Presenter{
        fun getData()
        fun saveData()
    }
}