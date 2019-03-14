package com.iffy.fikhustaz.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {
    const val BASE_URL = "https://ta-iffy.herokuapp.com/"

    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(makeOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(RetrofitService::class.java)
    }

    private fun makeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(makeLoggingInterceptor())
            .connectTimeout(240, TimeUnit.SECONDS)
            .readTimeout(240, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level =
            HttpLoggingInterceptor.Level.BODY
        return logging
    }
}