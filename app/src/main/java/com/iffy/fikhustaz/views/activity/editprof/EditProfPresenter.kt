package com.iffy.fikhustaz.views.activity.editprof

import com.iffy.fikhustaz.util.FirebaseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfPresenter(v: EditProfContract.View) : EditProfContract.Presenter {

    private val view = v
    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun getData() {
        view.showLoad()
        uiScope.launch {
            FirebaseUtil.getCurrentUser {
                view.setData(it)
                view.hideLoad()
            }
        }
    }

    override fun saveData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}