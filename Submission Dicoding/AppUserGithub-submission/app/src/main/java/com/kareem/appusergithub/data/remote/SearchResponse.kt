package com.kareem.appusergithub.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResponse (
    @field:SerializedName("items")
    val items: ArrayList<UserItems>
    ):Parcelable