package com.ksainthi.swifty.data.api

import android.content.res.Resources
import com.ksainthi.swifty.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiService @Inject constructor(private val resources: Resources) {
    private val httpClient = OkHttpClient().newBuilder().addInterceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .build()
        chain.proceed(request)
    }.callTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()


    private val client = Retrofit.Builder()
        .baseUrl(getBaseUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    private fun getBaseUrl() = resources.getString(R.string.api_base_url)

    fun createApi42(): Api42 {
        return client.create(Api42::class.java)
    }
}