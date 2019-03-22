package com.iffy.fikhustaz.views.activity.editprof

interface EditProfContract {
    interface View{
        fun showLoad()
        fun hideLoad()
        fun showTimePicker(v: View)
        fun showDatePicker(v: View)
    }
    interface Presenter{
        fun getData()
    }
}