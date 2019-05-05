package com.iffy.fikhustaz.views.fragment.quran

import com.iffy.fikhustaz.data.model.quran.Quran

interface QuranContract {
    interface View{
        fun showLoad()
        fun hideLoad()
        fun showMsg(str: String)
        fun setData(it: List<Quran>)
        fun setDataSql(it: List<Quran>)

    }
    interface Presenter {
        fun initData()
        fun initDataSQL()
    }
}