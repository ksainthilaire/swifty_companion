package com.ksainthi.swifty.domain.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Skill(
    val id: Int,
    val name: String,
    val level: Float
) : Parcelable