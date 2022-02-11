package    com.ksainthi.swifty

import androidx.annotation.Keep
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.serialization.*
import java.lang.System.exit


object Api42 {
    @Keep
    @Serializable
    data class Token(
        @SerialName("access_token") val accessToken: String,
        @SerialName("token_type") val tokenType: String,
        @SerialName("expires_in") val expiresIn: Int,
        @SerialName("scope") val scope: String,
        @SerialName("created_at") val createdAt: Int
    )


    @Keep
    @Serializable
    data class User(
        @SerialName("id") val id: Int,
        @SerialName("email") val email: String,
        @SerialName("login") val login: String? = null,
        @SerialName("first_name") val firstName: String? = null,
        @SerialName("last_name") val lastName: String? = null,

    )

    private val API_URL: String = "https://api.intra.42.fr"
    private val API_SECRET: String =
        "d622d2216b2d59e8ed20a0f0ff656a751db5abb666f34fa750af9a70d9ae2ca3"
    private val API_UID: String = "90552fdb65d07dc0eb4f8acccb0376a1d21a8ee358464adebec697ec6462bcf1"

    private var accessToken: String? = null

    suspend fun requestAPI(
        requestMethod: HttpMethod,
        path: String,
        params: MutableMap<String, String>?
    ): HttpResponse {

        val urlString = API_URL
            .plus(path)
        val client: HttpClient = HttpClient() {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }

        val response: HttpResponse = client.request(urlString) {

            method = requestMethod

            params?.forEach { (key, value) ->
                parameter(key, value)
            }

            headers {
                accessToken?.let {
                    println(it)
                    append(HttpHeaders.Authorization, "Bearer $it")
                }
            }
        }
        return response
    }


    suspend fun getToken(): Token {
        val params: MutableMap<String, String> = HashMap()

        params["grant_type"] = "client_credentials";
        params["client_secret"] = API_SECRET;
        params["client_id"] = API_UID;


        val response = requestAPI(HttpMethod.Post, "/oauth/token", params);
        val token: Token = response.receive()

        accessToken = token.accessToken
        return token
    }

    suspend fun getUser(login: String): User {
        val response: HttpResponse = requestAPI(HttpMethod.Get, "/v2/users/$login", null)
        val user: User = response.receive()
        return user
    }

}