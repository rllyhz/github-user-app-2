package id.rllyhz.githubuserapp.repository

import android.app.Application
import id.rllyhz.githubuserapp.BuildConfig
import id.rllyhz.githubuserapp.R
import id.rllyhz.githubuserapp.api.GithubApi
import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.util.DataConverter
import id.rllyhz.githubuserapp.util.Resource
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val githubApi: GithubApi,
    private val application: Application
) {
    suspend fun getUsers(): Resource<List<User>> {
        return try {
            val response = githubApi.getUsers(BuildConfig.GITHUB_API_TOKEN)
            val usersResponse = response.body()

            if (response.isSuccessful && usersResponse != null) {
                val allUsers = DataConverter.usersResponseToUsersModel(usersResponse)
                Resource.Success(allUsers)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(application.getString(R.string.error_message))
        }
    }

    suspend fun getUserDetail(username: String): Resource<User> {
        return try {
            val response = githubApi.getUserDetailOf(BuildConfig.GITHUB_API_TOKEN, username)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                val user = DataConverter.userDetailResponseToUserModel(result)
                Resource.Success(user)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(application.getString(R.string.error_message))
        }
    }

    suspend fun getFollowersOfUser(username: String): Resource<List<User>> {
        return try {
            val response = githubApi.getFollowersOfUser(BuildConfig.GITHUB_API_TOKEN, username)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                val users = DataConverter.usersResponseToUsersModel(result)
                Resource.Success(users)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(application.getString(R.string.error_message))
        }
    }

    suspend fun getFollowingOfUser(username: String): Resource<List<User>> {
        return try {
            val response = githubApi.getFollowingOfUser(BuildConfig.GITHUB_API_TOKEN, username)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                val users = DataConverter.usersResponseToUsersModel(result)
                Resource.Success(users)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(application.getString(R.string.error_message))
        }
    }

    suspend fun searchUsers(query: String): Resource<List<User>> {
        return try {
            val response = githubApi.searchUsers(BuildConfig.GITHUB_API_TOKEN, query)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                val users = DataConverter.searchUsersToUserModels(result)
                Resource.Success(users)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(application.getString(R.string.error_message))
        }
    }
}