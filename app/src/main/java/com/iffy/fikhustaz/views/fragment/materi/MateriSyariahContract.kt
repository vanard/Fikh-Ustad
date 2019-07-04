package com.iffy.fikhustaz.views.fragment.materi

import com.iffy.fikhustaz.data.model.materi.MateriUstad
import com.iffy.fikhustaz.data.model.materi.konsulsyariah.Fikih
import com.iffy.fikhustaz.data.model.materi.konsulsyariah.IsiFikh


interface MateriSyariahContract  {
        interface View{
            fun showMsg(msg: String)
            fun initData(list:List<Fikih>)
            fun listData(fikh : IsiFikh)
            fun showLoading()
            fun hideLoading()
            fun initMateri(materi: MateriUstad)
        }
        interface Presenter{
            fun getData(id:Int)
            fun getDetailData(link: String)
            fun getMateri()
        }
}