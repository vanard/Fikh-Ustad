package com.iffy.fikhustaz.views.fragment.materi

import android.util.Log.d
import com.iffy.fikhustaz.data.model.materi.MateriUstad
import com.iffy.fikhustaz.network.RetrofitFactory
import com.iffy.fikhustaz.util.FirebaseUtil.firestoreInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MateriSyariahPresenter (v: MateriSyariahContract.View) : MateriSyariahContract.Presenter {

    private var view: MateriSyariahContract.View = v
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val service = RetrofitFactory.makeRetrofitService(RetrofitFactory.BASE_URL_SYARIAH)

    override fun getData(id: Int) {
        view.showLoading()
        uiScope.launch {
            val request = service.fetchFikih(id)
            try {
                val response = request.await()
                if (response.isSuccessful){
                    response.body()?.let {
                        view.initData(it)
                    }
                }else{
                    d("MateriSyariah","${response.errorBody()}")
                    view.hideLoading()
                }
            } catch (e: HttpException) {
                d("MateriSyariah",e.code().toString())
            } catch (e: Throwable) {
                d("MateriSyariah", "$e")
            }

        }
    }

    override fun getDetailData(link: String) {
        view.showLoading()
        uiScope.launch {
            val request = service.fetchIsiFikh(link)
            try {
                val response = request.await()
                if (response.isSuccessful){
                    response.body()?.let {
                        view.listData(it[0])
                    }
                    view.hideLoading()
                }else{
                    d("MateriSyariah","${response.errorBody()}")
                    view.hideLoading()
                }
            } catch (e: HttpException) {
                d("MateriSyariah",e.code().toString())
            } catch (e: Throwable) {
                d("MateriSyariah", "$e")
            }

        }
    }

    override fun getMateri() {
        view.showLoading()
        firestoreInstance.collection("materi")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    view.initMateri(document.toObject(MateriUstad::class.java))
                }
            }
            .addOnFailureListener { exception ->
                d("PTK", "Error getting documents: ", exception)
            }
    }

}