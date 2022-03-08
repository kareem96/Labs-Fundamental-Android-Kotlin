package com.kareem.appusergithub.data.di

import android.content.Context
import com.kareem.appusergithub.data.Repository
import com.kareem.appusergithub.data.local.room.UserDatabase
import com.kareem.appusergithub.data.remote.ApiConfig
import com.kareem.appusergithub.utils.AppExecutors

object Injection {
    /*fun  provideRepository(context: Context): Repository{
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        val appExecutors = AppExecutors()
        return Repository.getInstance(apiService, dao, appExecutors)
    }*/
}