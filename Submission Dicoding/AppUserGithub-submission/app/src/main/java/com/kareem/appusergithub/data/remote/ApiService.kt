package com.kareem.appusergithub.data.remote



import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users/{username}")
    @Headers("Accept:application/json")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("search/users")
    @Headers("Accept:application/json")
    fun searchUser(
        @Query("q")
        query: String
    ):Call<SearchResponse>

    @GET("users/{username}/followers")
    @Headers("Accept:application/json")
    fun getFollowers(
        @Path("username")
        username: String
    ): Call<ArrayList<UserItems>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username")
        username: String
    ): Call<ArrayList<UserItems>>

}