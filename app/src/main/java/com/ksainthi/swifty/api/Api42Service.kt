package com.ksainthi.swifty.api

import com.ksainthi.swifty.viewmodels.Token
import com.ksainthi.swifty.viewmodels.User
import retrofit2.Call
import retrofit2.http.*


interface Api42Service {

    @POST("/oauth/token")
    fun login(
        @Query("client_secret") clientSecret: String,
        @Query("client_id") clientId: String,
        @Query("grant_type") grantType: String = "client_credentials",
    ) : Call<Token>

    @GET("/v2/users/{login}")
    fun getUser(
        @Header("Authorization") token: String,
        @Path("login") login: String
    ): Call<User>

    @GET("/v2/users")
    fun getUsers(
        @Header("Authorization") token: String,
        @Query("page[number]") page: Int
    ) : Call<Array<User>>

}