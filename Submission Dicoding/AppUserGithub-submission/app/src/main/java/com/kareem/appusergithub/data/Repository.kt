package com.kareem.appusergithub.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kareem.appusergithub.data.local.room.UserDao
import com.kareem.appusergithub.data.local.room.UserDatabase
import com.kareem.appusergithub.data.model.SearchResponse
import com.kareem.appusergithub.data.model.UserItems
import com.kareem.appusergithub.data.remote.ApiConfig
import com.kareem.appusergithub.data.remote.ApiService
import com.kareem.appusergithub.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository (private val application: Application){
    private val apiService: ApiService
    private val userDao: UserDao
//    private val appExecutors: AppExecutors,

    init {
        apiService = ApiConfig.getApiService()
        val database: UserDatabase = UserDatabase.getInstance(application)
        userDao = database.userDao()

    }
    /*private val result = MediatorLiveData<Result<List<UserEntity>>>()*/
    fun getSearch(query:String): LiveData<Result<List<UserItems>>> {
        val result = MutableLiveData<Result<List<UserItems>>>()
        result.postValue(Result.Loading())
        apiService.searchUser(query).enqueue(object : Callback<SearchResponse>{
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful){
                    val list = response.body()?.items
                    if(list.isNullOrEmpty())
                        result.postValue(Result.Error(null))
                    else
                        result.postValue(Result.Success(list))
                }
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                result.postValue(Result.Error(t.message))
            }
        })
        return result
    }
}
/*
{
    companion object{
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao,
            appExecutors: AppExecutors
        ):Repository = instance ?: synchronized(this){
            instance ?: Repository(apiService, userDao, appExecutors)
        }.also { instance = it }
    }
}*/
