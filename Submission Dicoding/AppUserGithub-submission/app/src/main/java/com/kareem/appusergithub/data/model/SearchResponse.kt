package com.kareem.appusergithub.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kareem.appusergithub.data.local.room.UserItems
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResponse (
    @field:SerializedName("items")
    val items: ArrayList<UserItems>
    ):Parcelable