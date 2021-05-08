package com.iffy.fikhustaz.network

import com.iffy.fikhustaz.data.model.Notification.NotificationRoot
import com.iffy.fikhustaz.data.model.hadist.Hadist
import com.iffy.fikhustaz.data.model.hadist.KitabHadist
import com.iffy.fikhustaz.data.model.materi.islamhouse.Kajian
import com.iffy.fikhustaz.data.model.materi.konsulsyariah.Fikih
import com.iffy.fikhustaz.data.model.materi.konsulsyariah.IsiFikh
import com.iffy.fikhustaz.data.model.quran.Quran
import com.iffy.fikhustaz.data.model.quran.Surat
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

const val SERVER_KEY = "AAAAHFSrcOI:APA91bFPVUp8DUgh7cZNNvgvvG-8KF3q24isFd8v9dGpWCpUOLDGV8H39efGqG5cADmre76WuxYLenpwiy_eg_nvIa1DMjCpNJObMS_rdCM4B7BefHl24espgrIi2teeMrKHVAPyRJpL"

interface RetrofitService {
    @GET("/articles")
    fun fetchArticles() : Deferred<Response<Kajian>>

    @GET("/fatwa")
    fun fetchFatwa() : Deferred<Response<Kajian>>

    @GET("/page/{page_id}")
    fun fetchFikih(@Path("page_id") page_id: Int): Deferred<Response<List<Fikih>>>

    @GET("/link/{link_page}")
    fun fetchIsiFikh(@Path("link_page") link_page: String): Deferred<Response<List<IsiFikh>>>

    @GET("/quran/")
    fun fetchQuran () : Deferred<Response<List<Quran>>>

    @GET("/quran/{surat}")
    fun fetchSurat(@Path("surat") surah: String): Deferred<Response<List<Surat>>>

    @GET("/hadist")
    fun fetchKitab(): Deferred<Response<List<KitabHadist>>>

    @GET("/hadist/{kitab}")
    fun fetchHadist(@Path("kitab") kitab: String, @Query("page") page : String): Deferred<Response<List<Hadist>>>

    @Headers(
        "Content-Type:application/json",
        "Authorization: Bearer $SERVER_KEY"
    )
    @POST("fcm/send")
    fun sendNotification(
        @Body data: NotificationRoot
    ): Call<ResponseBody>
}