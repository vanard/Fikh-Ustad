package com.iffy.fikhustaz.views.fragment.hadits

import com.iffy.fikhustaz.data.model.hadist.KitabHadist

interface HadistContract {
    interface View{
        fun showLoad()
        fun hideLoad()
        fun setData(kitab : List<KitabHadist>)
    }
    interface Presenter {
        fun initData()
    }
}