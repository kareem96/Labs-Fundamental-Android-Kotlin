package com.kareem.appusergithub.data.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UserItems (
    @field:SerializedName("id")
    var id: Int = 0,

    @field:SerializedName("login")
    var username: String = "",

    @field:SerializedName("name")
    var name: String = "",

    @field:SerializedName("avatar_url")
    var avatar: String = "",

    @field:SerializedName("company")
    var company: String? = null,

    @field:SerializedName("location")
    var location: String? = null,

    @field:SerializedName("followers")
    var followers: Int? = 0,

    @field:SerializedName("following")
    var following: Int? = 0,

    @field:SerializedName("public_repos")
    var repository: Int? = 0,
)