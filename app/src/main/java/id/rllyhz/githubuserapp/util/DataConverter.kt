package id.rllyhz.githubuserapp.util

import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.data.response.UsersResponse

object DataConverter {
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
}