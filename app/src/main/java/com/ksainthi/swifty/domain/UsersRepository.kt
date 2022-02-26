package com.ksainthi.swifty.domain

import com.ksainthi.swifty.data.api.ApiResult
import com.ksainthi.swifty.domain.model.Token
import com.ksainthi.swifty.domain.model.User
import kotlinx.coroutines.flow.Flow


interface UsersRepository {
    suspend fun getToken(): Flow<ApiResult<Token, Nothing>>
    suspend fun getUsers(page: String): Flow<ApiResult<Array<User>, Nothing>>
    suspend fun getUser(login: String): Flow<ApiResult<User, Nothing>>
}