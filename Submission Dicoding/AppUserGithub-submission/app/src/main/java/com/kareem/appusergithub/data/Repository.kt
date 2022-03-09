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

    fun getSearch(query:String): LiveData<Result<List<UserItems>>> {
        val result = MutableLiveData<Result<List<UserItems>>>()
        result.postValue(Result.Loading())
        apiService.searchUser(query).enqueue(object : Callback<SearchResponse>{
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                val list = response.body()?.items
                if(list.isNullOrEmpty())
                    result.postValue(Result.Error(null))
                else
                    result.postValue(Result.Success(list))
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                result.postValue(Result.Error(t.message))
            }
        })
        return result
    }

    suspend fun detailUser(username:String): LiveData<Result<UserItems>> {
        val user = MutableLiveData<Result<UserItems>>()
        if(userDao.getBookmarkedUser(username) != null){
            user.postValue(Result.Success(userDao.getBookmarkedUser(username)))
        }else{
            apiService.getUser(username).enqueue(object : Callback<UserItems>{
                override fun onResponse(call: Call<UserItems>, response: Response<UserItems>) {
                    val result = response.body()
                    user.postValue(Result.Success(result))
                }

                override fun onFailure(call: Call<UserItems>, t: Throwable) {

                }
            })
        }
        return user
    }

    fun getFollowers(username:String): LiveData<Result<List<UserItems>>>{
        val listFollowers = MutableLiveData<Result<List<UserItems>>>()
        listFollowers.postValue(Result.Loading())
        apiService.getFollowers(username).enqueue(object : Callback<List<UserItems>>{
            override fun onResponse(
                call: Call<List<UserItems>>,
                response: Response<List<UserItems>>
            ) {
                val list = response.body()
                if(list.isNullOrEmpty())
                    listFollowers.postValue(Result.Error(null))
                else
                    listFollowers.postValue(Result.Success(list))
            }

            override fun onFailure(call: Call<List<UserItems>>, t: Throwable) {
                listFollowers.postValue(Result.Error(t.message))
            }

        })
        return listFollowers
    }

    fun getFollowing(username:String): LiveData<Result<List<UserItems>>>{
        val listFollowing = MutableLiveData<Result<List<UserItems>>>()
        listFollowing.postValue(Result.Loading())
        apiService.getFollowing(username).enqueue(object : Callback<List<UserItems>>{
            override fun onResponse(
                call: Call<List<UserItems>>,
                response: Response<List<UserItems>>
            ) {
                val list = response.body()
                if(list.isNullOrEmpty())
                    listFollowing.postValue(Result.Error(null))
                else
                    listFollowing.postValue(Result.Success(list))
            }

            override fun onFailure(call: Call<List<UserItems>>, t: Throwable) {
                listFollowing.postValue(Result.Error(t.message))
            }

        })
        return listFollowing
    }

    suspend fun getBookmarkedUser(): LiveData<Result<List<UserItems>>>{
        val listBookmarked = MutableLiveData<Result<List<UserItems>>>()
        listBookmarked.postValue(Result.Loading())
        if(userDao.getListBookmark().isNullOrEmpty())
            listBookmarked.postValue(Result.Error(null))
        else
            listBookmarked.postValue(Result.Success(userDao.getListBookmark()))

        return listBookmarked
    }

    suspend fun insertBookmarkedUser(user: UserItems) = userDao.insertUser(user)
    suspend fun deleteBookmarkedUser(user: UserItems) = userDao.deleteAll(user)
}
