package com.kareem.appusergithub.data.remote

import com.kareem.appusergithub.data.model.SearchResponse
import com.kareem.appusergithub.data.model.UserItems
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users/{username}")
    fun getUser(
        @Path("username")
        username: String
    ): Call<UserItems>

    @GET("search/users")
    fun searchUser(
        @Query("q")
        query: String
    ):Call<SearchResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username")
        username: String
    ): Call<List<UserItems>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username")
        username: String
    ): Call<List<UserItems>>

}