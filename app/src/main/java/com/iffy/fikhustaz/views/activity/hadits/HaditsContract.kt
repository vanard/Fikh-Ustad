package com.iffy.fikhustaz.views.activity.hadits

import com.iffy.fikhustaz.data.model.hadist.Hadist

interface HaditsContract {
    interface View{
        fun showLoad()
        fun hideLoad()
        fun setData(hadist : List<Hadist>)
        fun updateData(hadist: List<Hadist>)
        fun loadData(hadits: List<Hadist>)
    }
    interface Presenter {
        fun initData(kitab: String, page: String)
        fun updateData(kitab: String, page: String)
        fun loadAllData(kitab: String, page: String)
    }
}