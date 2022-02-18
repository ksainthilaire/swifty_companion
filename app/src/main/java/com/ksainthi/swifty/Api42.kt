package    com.ksainthi.swifty


import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import com.ksainthi.swifty.viewmodels.*


object Api42 {

    private const val API_URL: String = "https://api.intra.42.fr"
    private const val API_SECRET: String =
        "d622d2216b2d59e8ed20a0f0ff656a751db5abb666f34fa750af9a70d9ae2ca3"
    private const val API_UID: String = "90552fdb65d07dc0eb4f8acccb0376a1d21a8ee358464adebec697ec6462bcf1"

    private var client: HttpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    private var accessToken: String? = null

    private suspend fun requestAPI(
        requestMethod: HttpMethod,
        path: String,
        params: MutableMap<String, String>?
    ): HttpResponse {

        val urlString = API_URL
            .plus(path)

        if (accessToken == null) {
            initWithToken()
        }
        Log.d("TAG", "accessToken() = $accessToken")

        val response: HttpResponse = client.request(urlString) {

            method = requestMethod

            params?.forEach { (key, value) ->
                parameter(key, value)
            }

            headers {
                append(HttpHeaders.Authorization, "Bearer $accessToken")
            }
        }
        return response
    }


    private suspend fun initWithToken() {

        val response: HttpResponse = client.post(API_URL.plus("/oauth/token")) {
            parameter("grant_type", "client_credentials")
            parameter("client_secret", API_SECRET)
            parameter("client_id", API_UID)
        }
        val token: Token = response.receive()
        Log.d("TAG", "Le token est ${token.accessToken}")
        accessToken = token.accessToken
    }

    suspend fun getUser(login: String): User {
        val response: HttpResponse = requestAPI(HttpMethod.Get, "/v2/users/$login", null)
        val user: User = response.receive()

        return user
    }

    suspend fun getCorrectionPointHistorics(login: String) : CorrectionPointHistory {
        val response: HttpResponse =
            requestAPI(HttpMethod.Get, " /v2/users/$login/correction_point_historics", null)

        val history: CorrectionPointHistory = response.receive()
        return history
    }

}