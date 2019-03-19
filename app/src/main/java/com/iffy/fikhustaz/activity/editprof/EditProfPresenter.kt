package com.iffy.fikhustaz.activity.editprof

class EditProfPresenter(v: EditProfContract.View) : EditProfContract.Presenter {

    private val view = v

    override fun getData() {
        view.showLoad()
    }

}