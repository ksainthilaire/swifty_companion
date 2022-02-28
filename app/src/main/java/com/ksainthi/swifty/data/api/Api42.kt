package com.ksainthi.swifty.data.api

import com.ksainthi.swifty.data.model.TokenResponse
import com.ksainthi.swifty.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface Api42 {

    @POST("/oauth/token")
    suspend fun login(
        @Query("client_secret") clientSecret: String,
        @Query("client_id") clientId: String,
        @Query("grant_type") grantType: String = "client_credentials",
    ) : Response<TokenResponse>

    @GET("/v2/users/{login}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("login") login: String
    ): Response<UserResponse>

    @GET("/v2/users")
    suspend fun getUsers(
        @Header("Authorization") token: String,
        @Query("page[number]") page: Int
    ) : Response<List<UserResponse>>

}