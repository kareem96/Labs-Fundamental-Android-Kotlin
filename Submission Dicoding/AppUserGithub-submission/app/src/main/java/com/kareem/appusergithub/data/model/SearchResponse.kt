package com.kareem.appusergithub.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @field:SerializedName("items")
    val items: List<UserItems>
    )