package com.iffy.fikhustaz.views.fragment.materi

import com.iffy.fikhustaz.data.model.materi.islamhouse.IslamData

interface MateriContract {
    interface View{
        fun showMsg(msg: String)
        fun initData(list:List<IslamData>)
        fun initDataFatwa(list:List<IslamData>)
        fun showLoading()
        fun hideLoading()
    }
    interface Presenter{
        fun getData()
        fun getDataFatwa()
    }
}