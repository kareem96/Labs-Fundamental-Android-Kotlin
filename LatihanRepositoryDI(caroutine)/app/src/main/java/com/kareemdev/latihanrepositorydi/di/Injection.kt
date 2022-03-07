package com.kareemdev.latihanrepositorydi.di

import android.content.Context
import com.kareemdev.latihanrepositorydi.data.NewsRepository
import com.kareemdev.latihanrepositorydi.data.local.room.NewsDatabase
import com.kareemdev.latihanrepositorydi.data.remote.retrofit.ApiConfig
import com.kareemdev.latihanrepositorydi.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        val appExecutors = AppExecutors()
        return NewsRepository.getInstance(apiService,dao, appExecutors)

    }
}