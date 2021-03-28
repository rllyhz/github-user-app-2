package id.rllyhz.githubuserapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
    val username: String,
    val avatarUrl: String,
    var fullName: String = "",
    val companyName: String = "",
    val blogUrl: String = "",
    val location: String = "",
    val email: String = "",
    val twitterUsername: String = "",
    val bio: String = "",
    val repositoriesCount: Int = 0,
    val gistsCount: Int = 0,
    val followersCount: Int = 0,
    val followingCount: Int = 0
) : Parcelable