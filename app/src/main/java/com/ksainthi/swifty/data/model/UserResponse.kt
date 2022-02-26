package com.ksainthi.swifty.data.model

import com.google.gson.annotations.SerializedName

data class Cursus(
    val id: Int,
    val name: String,
    val slug: String
)


data class SkillUser(
    val id: Int,
    val name: String,
    val level: Float
)


data class CursusUser(
    val id: Int,
    @SerializedName("grade") val grade: String? = null,
    @SerializedName("level") val level: Float,
    @SerializedName("skills") val skills: Array<SkillUser>,
    @SerializedName("cursus_id") val cursusId: Int,
    val cursus: Cursus
) {
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


data class Project(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("parent_id") val parentId: Int? = null
)


data class ProjectUser(
    @SerializedName("id") val id: Int,
    @SerializedName("occurrence") val occurrence: Int,
    @SerializedName("final_mark") val finalMark: String? = null,
    @SerializedName("status") val status: String,
    @SerializedName("validated?") val isValidated: Boolean? = null,
    @SerializedName("current_team_id") val currentTeamId: Int? = null,
    @SerializedName("project") val project: Project? = null,
    @SerializedName("cursus_ids") val cursusIds: Array<Int>,
) {
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

data class UserResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("login") val login: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("wallet") val wallet: Int,
    @SerializedName("image_url") val imageUrl: String? = null,
   @SerializedName("projects_users") val projectsUsers: List<ProjectUser>,
    val cursus_users: List<CursusUser>,
    @SerializedName("correction_point") val correctionPoint: Int
) {

    fun getCursusByName(name: String): CursusUser {
        for (cursusUser in cursus_users) {
            if (name == cursusUser.cursus.name)
                return cursusUser
        }
        return cursus_users.first()
    }

    fun getCursusNames(): Array<String> {
        return cursus_users.map { cursusUser -> cursusUser.cursus.name }.toTypedArray()
    }

    fun getParentProjects(cursusId: Int): Array<ProjectUser> {
        return this.getProjects(cursusId)
            .filter { projectUser ->
                projectUser.project != null && projectUser.project.parentId == null
            }.toTypedArray()
    }

    fun getChildProjects(cursusId: Int, parentId: Int): Array<ProjectUser> {
        return this.getProjects(cursusId)
            .filter { projectUser -> projectUser.project != null }
            .filter { projectUser -> projectUser.project?.parentId == parentId }
            .toTypedArray()
    }

    private fun getProjects(cursusId: Int): Array<ProjectUser> {
        return projectsUsers.filter { projectUser ->
            projectUser.cursusIds.contains(cursusId) && (projectUser.status == "in_progress"
                    || projectUser.status == "finished"
                    || projectUser.status == "parent")
        }.toTypedArray()
    }

    fun getSkills(cursusId: Int): Array<SkillUser> {
        return cursus_users.filter { cursusUser -> cursusUser.cursusId == cursusId }
            .map { cursusUser -> cursusUser.skills }.first()
    }

}


data class UserPicturesResponse(private val users: List<UserResponse>)