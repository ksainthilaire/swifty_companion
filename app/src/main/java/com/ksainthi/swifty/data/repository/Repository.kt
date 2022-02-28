package com.ksainthi.swifty.data.repository

import android.content.res.Resources
import android.util.Log
import com.ksainthi.swifty.R
import com.ksainthi.swifty.data.api.Api42
import com.ksainthi.swifty.data.api.ApiResult
import com.ksainthi.swifty.data.mapper.ApiMapper
import com.ksainthi.swifty.data.model.TokenResponse
import com.ksainthi.swifty.data.model.UserResponse
import com.ksainthi.swifty.domain.model.Token
import com.ksainthi.swifty.domain.model.User
import retrofit2.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val api: Api42,
    private val mapper: ApiMapper,
    private val resources: Resources
) {

    private lateinit var token: Token

    suspend fun updateTokenIfNecessary() {
        getToken().collect {
            result -> when(result) {
                is ApiResult.Success -> {
                    token = result.data
                }

                else -> {}
            }
        }
    }

    suspend fun getToken(): Flow<ApiResult<Token, Nothing>> {


        return flow {

            val response: Response<TokenResponse> = api.login(getClientSecret(), getClientId())
            if (response.isSuccessful) {
                val token: TokenResponse? = response.body()

                token?.let { emit(ApiResult.Success(mapper.mapToToken(token))) }
                Log.d("TAG", "Le token est ${token.toString()}")
            }
        }
    }



    suspend fun getUser(login: String): Flow<ApiResult<User, Nothing>> {
        //updateTokenIfNecessary()

        return flow {
            val response: Response<UserResponse> = api.getUser(token.accessToken, login)
            if (response.isSuccessful) {
                val user: UserResponse? = response.body()
                user?.let { emit(ApiResult.Success(mapper.mapToUser(user))) }
            }
        }
    }

    suspend fun getUsers(page: Int): Flow<ApiResult<List<User>, Nothing>> {
        updateTokenIfNecessary()
        return flow {
            val response: Response<List<UserResponse>> = api.getUsers(token.accessToken, page)
            if (response.isSuccessful()) {
                val users: List<UserResponse>? = response.body()
                users?.let {
                    emit(ApiResult.Success(mapper.mapToUsers(users)))
                }
            }
        }
    }


    fun getClientSecret(): String = resources.getString(R.string.api_secret)
    fun getClientId(): String = resources.getString(R.string.api_uid)

}