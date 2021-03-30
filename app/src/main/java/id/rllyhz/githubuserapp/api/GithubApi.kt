package id.rllyhz.githubuserapp.api

import id.rllyhz.githubuserapp.BuildConfig
import id.rllyhz.githubuserapp.data.response.SearchUsersResponse
import id.rllyhz.githubuserapp.data.response.UserDetailResponse
import id.rllyhz.githubuserapp.data.response.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    companion object {
        const val BASE_URL = "https://api.github.com/"
        const val TOKEN = BuildConfig.GITHUB_API_TOKEN
    }

    @GET("users")
    @Headers("Authorization: token $TOKEN")
    suspend fun getUsers(): Response<List<UsersResponse>>

    @GET("users/{username}")
    @Headers("Authorization: token $TOKEN")
    suspend fun getUserDetailOf(@Path("username") username: String): Response<UserDetailResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: token $TOKEN")
    suspend fun getFollowingOfUser(@Path("username") username: String): Response<List<UsersResponse>>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $TOKEN")
    suspend fun getFollowersOfUser(@Path("username") username: String): Response<List<UsersResponse>>

    @GET("search/users")
    @Headers("Authorization: token $TOKEN")
    suspend fun searchUsers(@Query("q") query: String): Response<SearchUsersResponse>
}