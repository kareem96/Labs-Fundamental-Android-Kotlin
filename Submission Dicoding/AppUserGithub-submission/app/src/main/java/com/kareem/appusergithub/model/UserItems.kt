package com.kareem.appusergithub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserItems (
    var id: Int = 0,
    var avatar: String = "",
    var username: String = "",
    var url: String = "",
    var followers: Int = 0,
    var following: Int = 0,
    var repository: Int = 0,
    var githubUrl: String? = null,
    var company: String? = null,
    var location: String? = null
) : Parcelable