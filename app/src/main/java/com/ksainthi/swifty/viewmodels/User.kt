package com.ksainthi.swifty.viewmodels

import android.os.Parcelable
import android.util.Log
import androidx.annotation.Keep
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Keep
@Serializable
@Parcelize
data class Cursus(
    val id: Int,
    val name: String,
    val slug: String
) : Parcelable

@Keep
@Serializable
@Parcelize
data class SkillUser(
    val id: Int,
    val name: String,
    val level: Float
) : Parcelable


@Keep
@Serializable
@Parcelize
data class CursusUser(
    val id: Int,
    @SerialName("grade") val grade: String? = null,
    @SerialName("level") val level: Float,
    @SerialName("skills") val skills: Array<SkillUser>,
    @SerialName("cursus_id") val cursusId: Int,
    val cursus: Cursus
) : Parcelable {

    fun getUserLevel(): String {
        Log.d("TAG", "le level est ${level}")
        return level.toString()
    }
}

@Keep
@Serializable
@Parcelize
data class Project(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("parent_id") val parentId: Int? = null
) : Parcelable

@Keep
@Serializable
@Parcelize
data class ProjectUser(
    @SerialName("id") val id: Int,
    @SerialName("occurrence") val occurrence: Int,
    @SerialName("final_mark") val finalMark: String? = null,
    @SerialName("status") val status: String,
    @SerialName("validated?") val isValidated: Boolean? = null,
    @SerialName("current_team_id") val currentTeamId: Int? = null,
    @SerialName("project") val project: Project? = null,
    @SerialName("cursus_ids") val cursusIds: Array<Int>,
) : Parcelable

@Keep
@Serializable
@Parcelize
data class User(
    @SerialName("id") val id: Int,
    @SerialName("login") val login: String? = null,
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    @SerialName("wallet") val wallet: Int,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("projects_users") val projectsUsers: Array<ProjectUser>,
    @SerialName("cursus_users") val cursusUsers: Array<CursusUser>,
    @SerialName("correction_point") val correctionPoint: Int
) : Parcelable {

    fun getCursusByName(name: String): CursusUser? {
        for (cursusUser in cursusUsers) {
            if (name == cursusUser.cursus.name)
                return cursusUser
        }
        return cursusUsers.get(0)
    }

    fun getCursusNames(): Array<String> {
        return cursusUsers.map { cursusUser -> cursusUser.cursus.name }.toTypedArray()
    }

    fun getParentProjects(cursusId: Int): Array<ProjectUser> {
        return this.getProjects(cursusId)
            .filter { projectUser ->
                projectUser.project != null && projectUser.project.parentId == null }.toTypedArray()
    }

    fun getChildProjects(cursusId: Int, parentId: Int): Array<ProjectUser> {
        return this.getProjects(cursusId)
            .filter { projectUser -> projectUser.project != null }
            .filter { projectUser -> projectUser.project?.parentId == parentId}
            .toTypedArray()
    }

    fun getProjects(cursusId: Int): Array<ProjectUser> {
        return projectsUsers.filter { projectUser ->
            projectUser.cursusIds.contains(cursusId) && (projectUser.status == "finished"
                    || projectUser.status == "parent")
        }.toTypedArray()
    }

    fun getSkills(cursusId: Int) : Array<SkillUser> {
        return cursusUsers.filter { cursusUser -> cursusUser.cursusId == cursusId }
            .map { cursusUser -> cursusUser.skills }.first()
    }
}


@Keep
@Serializable
@Parcelize
data class UserSummary(
    @SerialName("id") val id: Int,
    @SerialName("login") val login: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("new_image_url") val newImageUrl: String? = null
) : Parcelable