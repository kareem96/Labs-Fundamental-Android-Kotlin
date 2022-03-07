package com.kareem.appusergithub.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kareem.appusergithub.model.UserItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {
    val listGithubUser = MutableLiveData<ArrayList<UserItems>>()

    fun setGithubUser(context: Context){
        val listItems = ArrayList<UserItems>()
        val url = "https://api.github.com/users"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 7ee940a64fa696c7d1815a59daa053b2e2a5bea6")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let {
                    String(it)
                }
                try {
                    val list = JSONArray(result)
                    for (i in 0 until list.length()) {
                        val githubUser = list.getJSONObject(i)
                        val githubUserItem = UserItems()
                        githubUserItem.id = githubUser.getInt("id")
                        githubUserItem.avatar = githubUser.getString("avatar_url")
                        githubUserItem.username = githubUser.getString("login")
                        githubUserItem.url = githubUser.getString("html_url")
                        listItems.add(githubUserItem)
                    }
                    listGithubUser.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when(statusCode){
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun searchGithubUsers(context: Context, search: String){
        if (search == ""){
            val listItems = ArrayList<UserItems>()
            val url = "https://api.github.com/users"
            val client = AsyncHttpClient()
            client.addHeader("Authorization", "token 7ee940a64fa696c7d1815a59daa053b2e2a5bea6")
            client.addHeader("User-Agent", "request")

            client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?
                ) {
                    val result = responseBody?.let {
                        String(it)
                    }
                    try {
                        val list = JSONArray(result)
                        for (i in 0 until list.length()) {
                            val githubUser = list.getJSONObject(i)
                            val githubUserItems = UserItems()
                            githubUserItems.id = githubUser.getInt("id")
                            githubUserItems.avatar = githubUser.getString("avatar_url")
                            githubUserItems.username = githubUser.getString("login")
                            githubUserItems.url = githubUser.getString("url")
                            listItems.add(githubUserItems)
                        }
                        listGithubUser.postValue(listItems)
                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable?
                ) {
                    val  errorMessage = when (statusCode){
                        401 -> "$statusCode : Bad Request"
                        403 -> "$statusCode : Forbidden"
                        404 -> "$statusCode : Not Found"
                        else -> "$statusCode : ${error?.message}"
                    }
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            })
        }else{
            val listItems = ArrayList<UserItems>()

            val url = "https://api.github.com/search/users?q=$search"
            val client = AsyncHttpClient()
            client.addHeader("Authorization", "token 7ee940a64fa696c7d1815a59daa053b2e2a5bea6")
            client.addHeader("User-Agent", "request")
            listGithubUser.postValue(null)

            client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>,
                    responseBody: ByteArray
                ) {
                    val result = String(responseBody)
                    try {
                        val resultObject = JSONObject(result)
                        val list = resultObject.getJSONArray("items")
                        for (i in 0 until list.length()) {
                            val githubUser = list.getJSONObject(i)
                            val githubUserItem = UserItems()
                            githubUserItem.id = githubUser.getInt("id")
                            githubUserItem.avatar = githubUser.getString("avatar_url")
                            githubUserItem.username = githubUser.getString("login")
                            githubUserItem.url = githubUser.getString("url")
                            listItems.add(githubUserItem)
                        }
                        listGithubUser.postValue(listItems)
                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable?
                ) {
                 val errorMessage = when (statusCode){
                     401 -> "$statusCode : Bad Request"
                     403 -> "$statusCode : Forbidden"
                     404 -> "$statusCode : Not Found"
                     else -> "$statusCode : ${error?.message}"
                 }
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
    fun getGithubUsers() : LiveData<ArrayList<UserItems>>{
        return listGithubUser
    }

}