package com.iffy.fikhustaz.views.fragment.home

import com.iffy.fikhustaz.data.model.Ustad

interface HomeContract {
    interface View{
        fun showLoad()
        fun hideLoad()
        fun setData(ustad: Ustad)
    }
    interface Presenter{
        fun getData()
        fun cancelGetData()
    }
}