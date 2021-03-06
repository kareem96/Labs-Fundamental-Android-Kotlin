package com.kareem.appusergithub.data.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(

    @SerializedName("id")
    val id: Int,

    @SerializedName("login")
    val login: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("company")
    val company: String,

    @SerializedName("public_repos")
    val publicRepos: Int,

    @SerializedName("followers")
    val followers: Int,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("following")
    val following: Int,

    @SerializedName("location")
    val location: String,

)