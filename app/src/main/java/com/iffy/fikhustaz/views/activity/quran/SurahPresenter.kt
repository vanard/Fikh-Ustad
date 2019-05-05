package com.iffy.fikhustaz.views.activity.quran

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import android.util.Log.d
import com.iffy.fikhustaz.data.local.db
import com.iffy.fikhustaz.data.model.quran.Quran
import com.iffy.fikhustaz.data.model.quran.Surat
import com.iffy.fikhustaz.network.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import retrofit2.HttpException

class SurahPresenter (val v : SurahContract.View, context: Context) : SurahContract.Presenter {

    private val ctx = context
    private val view = v
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
                    d("MateriSyariah", "${response.errorBody()}")
                }
            } catch (e: HttpException) {
                d("MateriSyariah", e.code().toString())
            } catch (e: Throwable) {
                d("MateriSyariah", "$e")
            }
        }
    }

    override fun initDataSql(surah: String) {
        view.showLoad()
        uiScope.launch {
            try {
                ctx.db.use {
                    val res = select(Surat.TABLE_SURAH).whereArgs("(QURAN_ID = {id})",
                            "id" to surah)
                    val data = res.parseList(classParser<Surat>())

                    if (data.isEmpty()) {
                        d("PTK", "Set Ulang $surah")
                        view.hideLoad()
                        initData(surah)
                    } else {
                        d("PTK", "Set SQL $surah")
                        view.setDataSql(data)
                        view.hideLoad()
                    }
                }
            }catch (e: SQLiteConstraintException){
                view.showMsg(e.localizedMessage)
                Log.d("PTK", e.localizedMessage)
                view.hideLoad()
            }
        }
    }
}