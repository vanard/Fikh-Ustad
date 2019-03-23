package com.iffy.fikhustaz.views.fragment.home

import com.iffy.fikhustaz.util.FirebaseUtil
import kotlinx.coroutines.*

class HomePresenter(v: HomeContract.View) : HomeContract.Presenter{

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

    override fun cancelGetData(){
        Job().cancel()
    }

}