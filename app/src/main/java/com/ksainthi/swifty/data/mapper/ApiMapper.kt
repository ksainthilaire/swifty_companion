package com.ksainthi.swifty.data.mapper

import android.util.Log
import com.ksainthi.swifty.data.model.CursusUser
import com.ksainthi.swifty.data.model.ProjectUser
import com.ksainthi.swifty.data.model.TokenResponse
import com.ksainthi.swifty.data.model.UserResponse
import com.ksainthi.swifty.domain.model.*
import javax.inject.Inject

class ApiMapper @Inject constructor() {

    fun mapToToken(tokenResponse: TokenResponse): Token = Token(
        accessToken = "Bearer ${tokenResponse.accessToken}",
        expiresIn = tokenResponse.expiresIn,
        createdAt = tokenResponse.createdAt
    )

    private fun consolidateUserCursus(
        cursus: List<CursusUser>,
        projects: List<ProjectUser>
    ): List<Cursus> = cursus.map { it ->
        Cursus(
            id = it.cursusId,
            name = it.cursus.name,
            level = it.level,
            skills = it.skills.map { skill ->
                Skill(
                    id = skill.id,
                    name = skill.name,
                    level = skill.level
                )
            },
            projects = projects.filter { project -> project.cursusIds.contains(it.cursusId) }
                .map { it ->
                    Project(
                        id = it.id,
                        parentId = it.project?.parentId,
                        name = it.project?.name,
                        status = it.status,
                        finalMark = it.finalMark,
                        isValidated = it.isValidated
                    )
                }
        )
    }

    fun mapToUser(user: UserResponse): User = User(
        fullName = "${user.lastName} ${user.firstName}",
        picture = "${user.imageUrl}",
        login = "${user.login}",
        wallet = user.wallet,
        correctionPoints = user.correctionPoint,
        cursus = if (user.cursus_users != null) consolidateUserCursus(
            user.cursus_users,
            user.projectsUsers
        ) else null
    )


    fun mapToUsers(users: List<UserResponse>): List<User> {
        return users.map { user -> mapToUser(user) }
    }


}