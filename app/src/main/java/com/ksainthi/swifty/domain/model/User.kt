package com.ksainthi.swifty.domain.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class   User constructor(
    val fullName: String = "",
    val login: String = "",
    val level: String = "",
    val wallet: Int,
    val picture: String = "",
    val correctionPoints: Int,
    val cursus: List<Cursus>? = null
) : Parcelable {

    fun getLevelPercent(cursusId: Int): Int {
        val level: Float = cursus!!.first { cursus -> cursus.id == cursusId }.level
        val startLevel = level.toInt()
        return ((level - startLevel) * 100).toInt()
    }

}

