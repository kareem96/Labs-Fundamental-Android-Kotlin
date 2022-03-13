package com.kareem.appusergithub.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class UserEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name = "id")
    val id: Int,

    @field:ColumnInfo(name = "username")
    val login: String,

    @field:ColumnInfo(name = "image_path")
    var avatar_url: String,
)