package com.iffy.fikhustaz.views.activity.editprof

class EditProfPresenter(v: EditProfContract.View) : EditProfContract.Presenter {

    private val view = v

    override fun getData() {
        view.showLoad()
    }

}