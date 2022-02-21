package com.ksainthi.swifty.viewmodels

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
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
    @SerializedName("grade") val grade: String? = null,
    @SerializedName("level") val level: Float,
    @SerializedName("skills") val skills: Array<SkillUser>,
    @SerializedName("cursus_id") val cursusId: Int,
    val cursus: Cursus
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CursusUser

        if (id != other.id) return false
        if (grade != other.grade) return false
        if (level != other.level) return false
        if (!skills.contentEquals(other.skills)) return false
        if (cursusId != other.cursusId) return false
        if (cursus != other.cursus) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (grade?.hashCode() ?: 0)
        result = 31 * result + level.hashCode()
        result = 31 * result + skills.contentHashCode()
        result = 31 * result + cursusId
        result = 31 * result + cursus.hashCode()
        return result
    }
}

@Keep
@Serializable
@Parcelize
data class Project(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("parent_id") val parentId: Int? = null
) : Parcelable

@Keep
@Serializable
@Parcelize
data class ProjectUser(
    @SerializedName("id") val id: Int,
    @SerializedName("occurrence") val occurrence: Int,
    @SerializedName("final_mark") val finalMark: String? = null,
    @SerializedName("status") val status: String,
    @SerializedName("validated?") val isValidated: Boolean? = null,
    @SerializedName("current_team_id") val currentTeamId: Int? = null,
    @SerializedName("project") val project: Project? = null,
    @SerializedName("cursus_ids") val cursusIds: Array<Int>,
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProjectUser

        if (id != other.id) return false
        if (occurrence != other.occurrence) return false
        if (finalMark != other.finalMark) return false
        if (status != other.status) return false
        if (isValidated != other.isValidated) return false
        if (currentTeamId != other.currentTeamId) return false
        if (project != other.project) return false
        if (!cursusIds.contentEquals(other.cursusIds)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + occurrence
        result = 31 * result + (finalMark?.hashCode() ?: 0)
        result = 31 * result + status.hashCode()
        result = 31 * result + (isValidated?.hashCode() ?: 0)
        result = 31 * result + (currentTeamId ?: 0)
        result = 31 * result + (project?.hashCode() ?: 0)
        result = 31 * result + cursusIds.contentHashCode()
        return result
    }
}

@Keep
@Serializable
@Parcelize
data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("login") val login: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("wallet") val wallet: Int,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("projects_users") val projectsUsers: Array<ProjectUser>,
    @SerializedName("cursus_users") val cursusUsers: Array<CursusUser>,
    @SerializedName("correction_point") val correctionPoint: Int
) : Parcelable {

    fun getCursusByName(name: String): CursusUser {
        for (cursusUser in cursusUsers) {
            if (name == cursusUser.cursus.name)
                return cursusUser
        }
        return cursusUsers.first()
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

    private fun getProjects(cursusId: Int): Array<ProjectUser> {
        return projectsUsers.filter { projectUser ->
            projectUser.cursusIds.contains(cursusId) && (projectUser.status == "in_progress"
                    || projectUser.status == "finished"
                    || projectUser.status == "parent")
        }.toTypedArray()
    }

    fun getSkills(cursusId: Int) : Array<SkillUser> {
        return cursusUsers.filter { cursusUser -> cursusUser.cursusId == cursusId }
            .map { cursusUser -> cursusUser.skills }.first()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (login != other.login) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (wallet != other.wallet) return false
        if (imageUrl != other.imageUrl) return false
        if (!projectsUsers.contentEquals(other.projectsUsers)) return false
        if (!cursusUsers.contentEquals(other.cursusUsers)) return false
        if (correctionPoint != other.correctionPoint) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (login?.hashCode() ?: 0)
        result = 31 * result + (firstName?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + wallet
        result = 31 * result + (imageUrl?.hashCode() ?: 0)
        result = 31 * result + projectsUsers.contentHashCode()
        result = 31 * result + cursusUsers.contentHashCode()
        result = 31 * result + correctionPoint
        return result
    }
}
