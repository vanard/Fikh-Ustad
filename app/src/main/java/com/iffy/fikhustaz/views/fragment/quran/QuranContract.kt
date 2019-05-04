package com.iffy.fikhustaz.views.fragment.quran

import com.iffy.fikhustaz.data.model.quran.Quran

interface QuranContract {
    interface View{
        fun showLoad()
        fun hideLoad()
        fun setData(it: List<Quran>)

    }
    interface Presenter {
        fun initData()
    }
}