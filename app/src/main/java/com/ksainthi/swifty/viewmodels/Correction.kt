package com.ksainthi.swifty.viewmodels

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Parcelize
data class CorrectionPointHistory(
    @SerialName("id") val id: Int,
    @SerialName("scale_team_id") val scaleTeamId: Int? = null,
    @SerialName("reason") val finalMark: String? = null,
    val sum: Int,
    val total: Int
) : Parcelable
