package com.iffy.fikhustaz.fragment.materi

import com.iffy.fikhustaz.network.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MateriPresenter (v: MateriContract.View) : MateriContract.Presenter{

    private var view: MateriContract.View? = v
    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun getData() {
        view?.showLoading()
        val service = RetrofitFactory.makeRetrofitService()
        uiScope.launch {
            val request = service.fetchFatwa()
            try {
                val response = request.await()
                response.body()?.let {
                    view?.initData(it.data)
                }
                view?.hideLoading()
            } catch (e: HttpException) {
                view?.showMsg(e.code().toString())
            } catch (e: Throwable) {
                view?.showMsg("Ooops: Something else went wrong")
            }
        }
    }

}