package id.rllyhz.githubuserapp.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UsersResponse(
    val id: Int,
    val avatar_url: String,
    @SerializedName("login") @Expose val username: String
)