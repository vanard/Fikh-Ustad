package com.iffy.fikhustaz.views.activity.quran

import com.iffy.fikhustaz.data.model.quran.Surat

interface SurahContract {
    interface View {
        fun showLoad()
        fun hideLoad()
        fun setData(it : List<Surat>)
    }

    interface Presenter {
        fun initData(ayat: String)
    }
}