package com.kareem.appusergithub.presentation.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kareem.appusergithub.data.local.room.UserDao
import com.kareem.appusergithub.data.local.entity.UserEntity
import com.kareem.appusergithub.data.local.room.UserDatabase
import com.kareem.appusergithub.data.model.UserItems
import com.kareem.appusergithub.data.remote.ApiConfig
import com.kareem.appusergithub.data.response.SearchResponse
import com.kareem.appusergithub.data.remote.ApiService
import com.kareem.appusergithub.data.response.DetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository (application: Application) {

    private val apiService: ApiService = ApiConfig.getApiService()
    private val userDao: UserDao

    init {
        val dbUser: UserDatabase = UserDatabase.getInstance(application)
        userDao = dbUser.userDao()
    }


    ///
    private val _searchUser = MutableLiveData<ArrayList<UserItems>>()
    val searchUser: LiveData<ArrayList<UserItems>> = _searchUser
    fun getSearch(query: String): LiveData<ArrayList<UserItems>> {
        val searchList = MutableLiveData<ArrayList<UserItems>>()
        val retrofit = apiService.searchUser(query)
        retrofit.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    searchList.postValue(response.body()?.items)

                } else {
                    val message = when (response.code()){
                        401 -> "${response.code()} : Forbidden"
                        403 -> "${response.code()} : Bad Request"
                        404 -> "${response.code()} : Not Found"
                        else -> "${response.code()} : ${response.body()}"
                    }
                    Log.d("MainViewModel", "onResponse: $message")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.d("MainViewModel", "onFailure: ${t.message.toString()}")
            }
        })
        return searchList
    }


    private val _detailUser = MutableLiveData<DetailResponse>()
    fun getDetailUser(username: String): LiveData<DetailResponse> {
        val retrofit = apiService.getDetailUser(username)
        retrofit.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    val message = when (response.code()){
                        401 -> "${response.code()} : Forbidden"
                        403 -> "${response.code()} : Bad Request"
                        404 -> "${response.code()} : Not Found"
                        else -> "${response.code()} : ${response.body()}"
                    }
                    Log.d("TAG", "onResponse: $message")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message.toString()}")
            }
        })
        return _detailUser
    }


    private val _follower = MutableLiveData<ArrayList<UserItems>>()
    fun getFollowers(username: String): LiveData<ArrayList<UserItems>> {
        val retrofit = apiService.getFollowers(username)
        retrofit.enqueue(object : Callback<ArrayList<UserItems>> {
            override fun onResponse(
                call: Call<ArrayList<UserItems>>,
                response: Response<ArrayList<UserItems>>
            ) {

                if (response.isSuccessful) {
                    _follower.value = response.body()
                } else {
                    val message = when (response.code()){
                        401 -> "${response.code()} : Forbidden"
                        403 -> "${response.code()} : Bad Request"
                        404 -> "${response.code()} : Not Found"
                        else -> "${response.code()} : ${response.body()}"
                    }
                    Log.d("TAG", "onResponse: $message")
                }
            }

            override fun onFailure(call: Call<ArrayList<UserItems>>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message.toString()}")
            }
        })
        return _follower
    }


    private val _following = MutableLiveData<ArrayList<UserItems>>()
    val following: LiveData<ArrayList<UserItems>> = _following
    fun getFollowing(username: String):LiveData<ArrayList<UserItems>> {
        val retrofit = apiService.getFollowing(username)
        retrofit.enqueue(object : Callback<ArrayList<UserItems>> {
            override fun onResponse(
                call: Call<ArrayList<UserItems>>,
                response: Response<ArrayList<UserItems>>
            ) {
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    val message = when (response.code()){
                        401 -> "${response.code()} : Forbidden"
                        403 -> "${response.code()} : Bad Request"
                        404 -> "${response.code()} : Not Found"
                        else -> "${response.code()} : ${response.body()}"
                    }
                    Log.d("TAG", "onResponse: $message")
                }
            }

            override fun onFailure(call: Call<ArrayList<UserItems>>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message.toString()}")
            }
        })
        return _following
    }


    fun insertStar(id:Int, username:String, avatarUrl:String) {
        CoroutineScope(Dispatchers.IO).launch {
        val uEntity = UserEntity(
            id, username,avatarUrl
        )
        userDao.insertUser(uEntity)
        }
    }

    fun deleteStar(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteAll(id)
        }
    }

    fun getAllStar(): LiveData<List<UserEntity>> = userDao.getListStar()

    suspend fun getStarUser(id: Int) = userDao.getStarUser(id)

}

