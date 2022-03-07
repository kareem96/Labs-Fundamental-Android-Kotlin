package com.kareemdev.latihanrepositorydi.data.remote.retrofit

import com.kareemdev.latihanrepositorydi.data.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines?country=id&category=science")
    fun getNews(
        @Query("apikey")
        apikey:String): Call<NewsResponse>
}