package id.rllyhz.githubuserapp.repository

import id.rllyhz.githubuserapp.api.GithubApi
import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.util.DataConverter
import id.rllyhz.githubuserapp.util.Resource
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val githubApi: GithubApi
) {
    suspend fun getUsers(): Resource<List<User>> {
        return try {
            val response = githubApi.getUsers()
            val usersResponse = response.body()

            if (response.isSuccessful && usersResponse != null) {
                val allUsers = DataConverter.usersResponseToUsersModel(usersResponse)
                Resource.Success(allUsers)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Something went wrong")
        }
    }
}