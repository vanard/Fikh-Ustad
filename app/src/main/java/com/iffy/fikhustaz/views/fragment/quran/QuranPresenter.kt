package com.iffy.fikhustaz.views.fragment.quran

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import android.util.Log.d
import com.iffy.fikhustaz.data.local.db
import com.iffy.fikhustaz.data.model.quran.Quran
import com.iffy.fikhustaz.network.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import retrofit2.HttpException

class QuranPresenter (val v: QuranContract.View, context: Context) : QuranContract.Presenter {

    private val ctx = context
    private val view = v
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val service = RetrofitFactory.makeRetrofitService(RetrofitFactory.BASE_URL_SYARIAH)

    override fun initData() {
        view.showLoad()
        uiScope.launch {
            val request = service.fetchQuran()
            try {
                val response = request.await()
                if (response.isSuccessful){
                    response.body()?.let {

                        view.setData(it)
                        view.hideLoad()
                    }
                }else{
                    view.hideLoad()
                    d("MateriSyariah", "${response.errorBody()}")
                }
            } catch (e: HttpException) {
                d("MateriSyariah", e.code().toString())
            } catch (e: Throwable) {
                d("MateriSyariah", "$e")
            }
        }
    }

    override fun initDataSQL() {
        view.showLoad()
        uiScope.launch {
            try {
                ctx.db.use {
                    val res = select(Quran.TABLE_QURAN)
                    val data = res.parseList(classParser<Quran>())
                    if (data.isEmpty()) {
                        view.hideLoad()
                        initData()
                    } else {
                        view.setDataSql(data)
                        view.hideLoad()
                    }
                }
            }catch (e: SQLiteConstraintException){
                view.showMsg(e.localizedMessage)
                d("PTK", e.localizedMessage)
                view.hideLoad()
            }
        }
    }

}