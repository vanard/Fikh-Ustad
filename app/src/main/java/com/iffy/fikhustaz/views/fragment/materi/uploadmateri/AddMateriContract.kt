package com.iffy.fikhustaz.views.fragment.materi.uploadmateri

import android.net.Uri

interface AddMateriContract {
    interface View{
        fun showLoad()
        fun hideLoad()
        fun showMsg(msg: String)
    }
    interface Presenter{
        fun uploadMateri(uid: String?, title: String, mFile: Uri?, thumb: ByteArray?)
    }
}