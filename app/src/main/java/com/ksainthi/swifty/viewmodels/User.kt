package com.ksainthi.swifty.viewmodels

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
 class User(
    @SerialName("id") val id: Int,
    @SerialName("login") val login: String? = null,
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    @SerialName("wallet") val wallet: Int,
    @SerialName("image_url") val imageUrl: String? = null
)