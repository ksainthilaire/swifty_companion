package com.ksainthi.swifty.viewmodels

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class Cursus(
    val id: Int,
    val name: String,
    val slug: String
)

@Keep
@Serializable
data class SkillUser(
    val id: Int,
    val name: String,
    val level: Float
)


@Keep
@Serializable
data class CursusUser(
    val id: Int,
    @SerialName("grade") val grade: String,
    @SerialName("level") val level: Float,
    @SerialName("skills") val skills: Array<SkillUser>,
    @SerialName("cursus_id") val cursusId: Int,
    val cursus: Cursus
)

@Keep
@Serializable
data class Project(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)

@Keep
@Serializable
data class ProjectUser(
    @SerialName("id") val id: Int,
    @SerialName("occurrence") val occurrence: Int,
    @SerialName("final_mark") val finalMark: String? = null,
    @SerialName("status") val status: String,
    @SerialName("validated?") val isValidated: Boolean? = null,
    @SerialName("current_team_id") val currentTeamId: Int? = null,
    @SerialName("project") val project: Project? = null,
    @SerialName("cursus_ids") val cursusIds: Array<Int>,
)

@Keep
@Serializable
class User(
    @SerialName("id") val id: Int,
    @SerialName("login") val login: String? = null,
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    @SerialName("wallet") val wallet: Int,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("projects_users") val projectsUsers: Array<ProjectUser>
)