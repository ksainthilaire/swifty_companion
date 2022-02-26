package com.ksainthi.swifty.domain.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Project(
    val id: Int,
    val parentId: Int?,
    val name: String?,
    val status: String,
    val finalMark: String?,
    val isValidated: Boolean?
) : Parcelable