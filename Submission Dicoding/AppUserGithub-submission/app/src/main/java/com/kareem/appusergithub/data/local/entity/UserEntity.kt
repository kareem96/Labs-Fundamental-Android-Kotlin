package com.kareem.appusergithub.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
class UserEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name="username")
    val username: String,

    @field:ColumnInfo(name="avatar_url")
    val avatar: String,

    @field:ColumnInfo(name="bookmarked")
    var isBookmarked: Boolean,
)