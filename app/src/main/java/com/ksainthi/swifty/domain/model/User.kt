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
        val level: Float = cursus?.filter { cursus -> cursus.id == cursusId }?.first()?.level ?: 0f 
        val startLevel = level.toInt()
        return ((level - startLevel) * 100).toInt()
    }

    fun getCursusNames(): List<String>?= cursus?.map { cursus -> cursus.name }
    fun getCursusByName(cursusName: String): Cursus? = cursus?.filter { it.name == cursusName}?.firstOrNull()
    fun getDefaultCursus(): Cursus? = getCursusByName("42cursus") ?: cursus?.first()
}

