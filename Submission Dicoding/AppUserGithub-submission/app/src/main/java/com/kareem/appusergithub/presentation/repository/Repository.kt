package com.kareem.appusergithub.presentation.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kareem.appusergithub.data.local.room.UserDao
import com.kareem.appusergithub.data.local.room.UserItems
import com.kareem.appusergithub.data.model.SearchResponse

import com.kareem.appusergithub.data.remote.ApiService
import com.kareem.appusergithub.data.response.DetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    //    private val appExecutors: AppExecutors,
){

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(apiService: ApiService, userDao: UserDao): Repository =
            instance ?: synchronized(this){
                instance ?: Repository(apiService, userDao)
            }.also { instance = it }
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    ///
    private val _searchUser = MutableLiveData<ArrayList<UserItems>>()
    val searchUser:LiveData<ArrayList<UserItems>> = _searchUser
    fun getSearch(query:String){
        _isLoading.value = true
        val retrofit = apiService.searchUser(query)
        retrofit.enqueue(object : Callback<SearchResponse>{
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _searchUser.value = response.body()?.items as ArrayList<UserItems>?
                }else{
                    Log.d("MainViewModel", "onResponse: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.d("MainViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }

    /*
    *
    */
    private val _detailUser = MutableLiveData<DetailResponse>()
    val detailUser:LiveData<DetailResponse> = _detailUser
    fun getDetailUser(username:String){
        _isLoading.value = true
        val retrofit = apiService.getDetailUser(username)
        retrofit.enqueue(object : Callback<DetailResponse>{
            override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.d("TAG", "onResponse: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message.toString()}")
            }
        })
    }



}

