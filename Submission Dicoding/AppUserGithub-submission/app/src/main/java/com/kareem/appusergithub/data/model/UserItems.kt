package com.kareem.appusergithub.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class UserItems (
    @field:SerializedName("id")
    @field:ColumnInfo(name="id")
    @field:PrimaryKey
    val id: Int = 0,

    @field:SerializedName("login")
    @field:ColumnInfo(name="username")
    val username: String = "",

    @field:SerializedName("name")
    @field:ColumnInfo(name="name")
    val name: String = "",

    @field:SerializedName("avatar_url")
    @field:ColumnInfo(name="avatar_url")
    val avatar: String? = "",

    @field:SerializedName("company")
    @field:ColumnInfo(name="company")
    val company: String? = null,

    @field:SerializedName("location")
    @field:ColumnInfo(name="location")
    val location: String? = null,

    @field:SerializedName("followers")
    @field:ColumnInfo(name="followers")
    val followers: Int? = 0,

    @field:SerializedName("following")
    @field:ColumnInfo(name="following")
    val following: Int? = 0,

    @field:SerializedName("public_repos")
    @field:ColumnInfo(name="public_repos")
    val repository: Int? = 0,

    @field:ColumnInfo(name="bookmarked")
    var isBookmarked: Boolean,
)