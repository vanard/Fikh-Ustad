package com.iffy.fikhustaz.network

import com.iffy.fikhustaz.data.model.materi.islamhouse.Kajian
import com.iffy.fikhustaz.data.model.materi.konsulsyariah.Fikih
import com.iffy.fikhustaz.data.model.materi.konsulsyariah.IsiFikh
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Path

interface RetrofitService {
    @GET("/articles")
    fun fetchArticles() : Deferred<Response<Kajian>>
    @GET("/fatwa")
    fun fetchFatwa() : Deferred<Response<Kajian>>
    @GET("/page/{page_id}")
    fun fetchFikih(@Path("page_id") page_id: Int): Deferred<Response<Fikih>>
    @GET("/link/{link_page}")
    fun fetchIsiFikh(@Path("link_page") link_page: String): Deferred<Response<IsiFikh>>
}