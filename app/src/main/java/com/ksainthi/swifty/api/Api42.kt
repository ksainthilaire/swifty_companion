package    com.ksainthi.swifty.api

import com.ksainthi.swifty.Constants.API_42_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Api42 {

    private lateinit var service: Api42Service
    var accessToken: String? = null
    set(token) {

        field = "Bearer ".plus(token)
        println("Le token est $field")
    }

    fun getService(): Api42Service {
        if (!::service.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(API_42_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            service = retrofit.create(Api42Service::class.java)
        }
        return service
    }
}