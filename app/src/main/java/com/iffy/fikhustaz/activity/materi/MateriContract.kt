package com.iffy.fikhustaz.activity.materi

import java.io.File

interface MateriContract {
    interface View{
        fun showLoading()
        fun hideLoading()
        fun materiToUI(pdfFile: File?)
        fun showMsg(msg: String)
    }
    interface Presenter{
        fun showFile(url: String)
    }
}