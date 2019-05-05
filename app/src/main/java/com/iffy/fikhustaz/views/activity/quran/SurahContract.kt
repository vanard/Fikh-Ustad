package com.iffy.fikhustaz.views.activity.quran

import com.iffy.fikhustaz.data.model.quran.Surat

interface SurahContract {
    interface View {
        fun showLoad()
        fun hideLoad()
        fun showMsg(str: String)
        fun setData(it : List<Surat>)
        fun setDataSql( it : List<Surat>)
    }

    interface Presenter {
        fun initData(ayat: String)
        fun initDataSql(surah: String)
    }
}