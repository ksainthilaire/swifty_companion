package com.ksainthi.swifty.data.api

import android.content.res.Resources
import com.ksainthi.swifty.R
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiService @Inject constructor(private val resources: Resources) {
    val httpClient = OkHttpClient().newBuilder().addInterceptor(object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
                .newBuilder()
                .build()
            return chain.proceed(request)
        }
    }).callTimeout(30, TimeUnit.SECONDS)
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