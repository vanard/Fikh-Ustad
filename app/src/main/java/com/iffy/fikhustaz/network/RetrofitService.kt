package com.iffy.fikhustaz.network

import com.iffy.fikhustaz.data.model.materi.Kajian
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.Response

interface RetrofitService {
    @GET("/articles")
    fun fetchArticles() : Deferred<Response<Kajian>>
    @GET("/fatwa")
    fun fetchFatwa() : Deferred<Response<Kajian>>
}