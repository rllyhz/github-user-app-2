package id.rllyhz.githubuserapp.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    val id: Int,
    @SerializedName("login") @Expose val username: String,
    @SerializedName("avatar_url") @Expose val avatarUrl: String,
    @SerializedName("name") @Expose val fullName: String?,
    @SerializedName("company") @Expose val companyName: String?,
    @SerializedName("blog") @Expose val blogUrl: String?,
    val location: String?,
    val email: String?,
    @SerializedName("twitter_username") @Expose val twitterUsername: String?,
    val bio: String?,
    @SerializedName("public_repos") @Expose val repositoriesCount: Int,
    @SerializedName("public_gists") @Expose val gistsCount: Int,
    @SerializedName("followers") @Expose val followersCount: Int,
    @SerializedName("following") @Expose val followingCount: Int,
)