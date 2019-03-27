package com.iffy.fikhustaz.views.fragment.materi

import android.util.Log.d
import com.iffy.fikhustaz.network.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MateriPresenter (v: MateriContract.View) : MateriContract.Presenter{

    private var view: MateriContract.View? = v
    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun getData() {
        view?.showLoading()
        val service = RetrofitFactory.makeRetrofitService()
        uiScope.async {
            val request = service.fetchArticles()
            try {
                val response = request.await()
                if (response.isSuccessful){
                    response.body()?.let {
                        d("MateriPresenter", "${it.data}")
                        view?.initData(it.data)
                    }
                    view?.hideLoading()
                }else{
                    view?.showMsg("${response.errorBody()}")
                    view?.hideLoading()
                }
            } catch (e: HttpException) {
                view?.showMsg(e.code().toString())
            } catch (e: Throwable) {
                view?.showMsg("$e")
                d("MateriPresenter", "$e")
            }
        }

    }

}