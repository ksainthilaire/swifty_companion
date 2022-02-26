package com.ksainthi.swifty.domain.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cursus(
    val id: Int,
    val name: String,
    val level: Float,
    val skills: List<Skill>,
    val projects: List<Project>
) : Parcelable
