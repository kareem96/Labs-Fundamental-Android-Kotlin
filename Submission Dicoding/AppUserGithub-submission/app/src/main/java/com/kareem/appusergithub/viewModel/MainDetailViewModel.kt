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

class MainDetailViewModel : ViewModel() {
    val listGithubUser = MutableLiveData<UserItems>()

    fun setGithubUsers(context: Context, username: String){
        val url = "https://api.github.com/users/$username"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 7ee940a64fa696c7d1815a59daa053b2e2a5bea6")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = String(responseBody!!)
                try {
                    val objectResult = JSONObject(result)
                    val githubUserItem = UserItems()
                    githubUserItem.id = objectResult.getInt("id")
                    githubUserItem.avatar = objectResult.getString("avatar_url")
                    githubUserItem.username = objectResult.getString("login")
                    githubUserItem.followers = objectResult.getInt("followers")
                    githubUserItem.following = objectResult.getInt("following")
                    githubUserItem.location = objectResult.getString("location")
                    githubUserItem.repository = objectResult.getInt("public_repos")
                    githubUserItem.url = objectResult.getString("html_url")
                    listGithubUser.postValue(githubUserItem)
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
    fun getGithubUsers() : LiveData<UserItems>{
        return listGithubUser
    }
}