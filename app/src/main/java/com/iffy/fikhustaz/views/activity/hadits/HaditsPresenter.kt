package com.iffy.fikhustaz.views.activity.hadits

import android.util.Log
import com.iffy.fikhustaz.network.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HaditsPresenter (val v : HaditsContract.View) : HaditsContract.Presenter {

    private val vv = v
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val service = RetrofitFactory.makeRetrofitService(RetrofitFactory.BASE_URL_SYARIAH)

    override fun initData(kitab: String, page: String) {
        vv.showLoad()
        uiScope.launch {
            val request = service.fetchHadist(kitab, page)
            try {
                val response = request.await()
                if (response.isSuccessful){
                    response.body()?.let {

                        vv.setData(it)
                        vv.hideLoad()
                    }
                }else{
                    vv.hideLoad()
                    Log.d("HadistPresenter", "${response.errorBody()}")
                }
            } catch (e: HttpException) {
                vv.hideLoad()
                Log.d("HadistPresenter", e.code().toString())
            } catch (e: Throwable) {
                vv.hideLoad()
                Log.d("HadistPresenter", "$e")
            }
        }
    }

    override fun updateData(kitab: String, page: String) {
        uiScope.launch {
            val request = service.fetchHadist(kitab, page)
            try {
                val response = request.await()
                if (response.isSuccessful){
                    response.body()?.let {

                        vv.updateData(it)
                    }
                }else{
                    Log.d("HadistPresenter", "${response.errorBody()}")
                }
            } catch (e: HttpException) {
                Log.d("HadistPresenter", e.code().toString())
            } catch (e: Throwable) {
                Log.d("HadistPresenter", "$e")
            }
        }
    }

    override fun loadAllData(kitab: String, page: String) {
        uiScope.launch {
            val request = service.fetchHadist(kitab, page)
            try {
                val response = request.await()
                if (response.isSuccessful){
                    response.body()?.let {

                        vv.loadData(it)
                    }
                }else{
                    Log.d("HadistPresenter", "${response.errorBody()}")
                }
            } catch (e: HttpException) {
                Log.d("HadistPresenter", e.code().toString())
            } catch (e: Throwable) {
                Log.d("HadistPresenter", "$e")
            }
        }
    }

}