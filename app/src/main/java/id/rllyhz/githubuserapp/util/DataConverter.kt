package id.rllyhz.githubuserapp.util

import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.data.response.UserDetailResponse
import id.rllyhz.githubuserapp.data.response.UsersResponse

object DataConverter {
    val STRING_NULL = "-"

    fun usersResponseToUsersModel(usersResponse: List<UsersResponse>): List<User> {
        val allUsers = mutableListOf<User>()

        for (user in usersResponse) {
            user.apply {
                allUsers.add(
                    User(
                        id = id,
                        username = username,
                        avatarUrl = avatar_url
                    )
                )
            }
        }

        return allUsers
    }

    fun userDetailResponseToUserModel(userResponse: UserDetailResponse): User =
        User(
            userResponse.id,
            userResponse.username,
            userResponse.avatarUrl,
            userResponse.fullName ?: STRING_NULL,
            userResponse.companyName ?: STRING_NULL,
            userResponse.blogUrl ?: STRING_NULL,
            userResponse.location ?: STRING_NULL,
            userResponse.email ?: STRING_NULL,
            userResponse.twitterUsername ?: STRING_NULL,
            userResponse.bio ?: STRING_NULL,
            userResponse.repositoriesCount,
            userResponse.gistsCount,
            userResponse.followersCount,
            userResponse.followingCount,
        )
}