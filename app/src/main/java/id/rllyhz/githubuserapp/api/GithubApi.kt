package id.rllyhz.githubuserapp.api

import id.rllyhz.githubuserapp.data.response.SearchUsersResponse
import id.rllyhz.githubuserapp.data.response.UserDetailResponse
import id.rllyhz.githubuserapp.data.response.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @GET("users")
    suspend fun getUsers(
        @Header("Authorization") token: String
    ): Response<List<UsersResponse>>

    @GET("users/{username}")
    suspend fun getUserDetailOf(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Response<UserDetailResponse>

    @GET("users/{username}/following")
    suspend fun getFollowingOfUser(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Response<List<UsersResponse>>

    @GET("users/{username}/followers")
    suspend fun getFollowersOfUser(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Response<List<UsersResponse>>

    @GET("search/users")
    suspend fun searchUsers(
        @Header("Authorization") token: String,
        @Query("q") query: String
    ): Response<SearchUsersResponse>
}