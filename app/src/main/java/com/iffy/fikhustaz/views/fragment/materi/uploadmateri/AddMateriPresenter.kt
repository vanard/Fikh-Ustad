package com.iffy.fikhustaz.views.fragment.materi.uploadmateri

import android.net.Uri
import com.iffy.fikhustaz.util.FirebaseUtil

class AddMateriPresenter (view: AddMateriContract.View) : AddMateriContract.Presenter {

    val v = view

    override fun uploadMateri(uid: String?, title: String, mFile: Uri?, thumb: ByteArray?) {
        if (uid == null)
            return v.showMsg("You must login first")
        if(title.isEmpty() || title.isBlank()) return v.showMsg("Judul materi harus diisi")
        if (thumb == null) return v.showMsg("Thumbnail materi belum terpilih")
        if (mFile == null) return v.showMsg("Materi belum terpilih")

        v.showLoad()
        FirebaseUtil.putMateriFile(uid, title, mFile, thumb,this::complete)
    }

    private fun complete(){
        v.hideLoad()
        v.showMsg("Add Materi Successfully")
    }

}