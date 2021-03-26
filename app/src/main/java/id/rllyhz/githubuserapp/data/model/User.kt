package id.rllyhz.githubuserapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
    val avatar_url: String,
    @SerializedName("login") @Expose val username: String
) : Parcelable