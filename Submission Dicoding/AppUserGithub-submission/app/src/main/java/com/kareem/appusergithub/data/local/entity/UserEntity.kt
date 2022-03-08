package com.kareem.appusergithub.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "USER")
class UserEntity (
    @field:ColumnInfo(name="id")
    @field:PrimaryKey
    var id: Int = 0,

    @field:ColumnInfo(name="username")
    var username: String = "",

    @field:ColumnInfo(name="name")
    var name: String = "",

    @field:ColumnInfo(name="avatar_url")
    var avatar: String = "",

    @field:ColumnInfo(name="company")
    var company: String? = null,

    @field:ColumnInfo(name="location")
    var location: String? = null,

    @field:ColumnInfo(name="followers")
    var followers: Int? = 0,

    @field:ColumnInfo(name="following")
    var following: Int? = 0,

    @field:ColumnInfo(name="public_repos")
    var repository: Int? = 0,

    @field:ColumnInfo(name="bookmarked")
    var isBookmarked: Boolean,
)