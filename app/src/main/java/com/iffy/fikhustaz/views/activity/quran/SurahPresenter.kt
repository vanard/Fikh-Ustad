package com.iffy.fikhustaz.views.activity.quran

import android.util.Log
import com.iffy.fikhustaz.network.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SurahPresenter (val v : SurahContract.View) : SurahContract.Presenter {
    private var view = v
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val service = RetrofitFactory.makeRetrofitService(RetrofitFactory.BASE_URL_SYARIAH)

    override fun initData(ayat: String) {
        view.showLoad()
        uiScope.launch {
            val request = service.fetchSurat(ayat)
            try {
                val response = request.await()
                if (response.isSuccessful){
                    response.body()?.let {
                        view.setData(it)
                        view.hideLoad()
                    }
                }else{
                    view.hideLoad()
                    Log.d("MateriSyariah", "${response.errorBody()}")
                }
            } catch (e: HttpException) {
                Log.d("MateriSyariah", e.code().toString())
            } catch (e: Throwable) {
                Log.d("MateriSyariah", "$e")
            }
        }
    }
}