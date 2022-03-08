package com.kareem.appusergithub.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kareem.appusergithub.data.Repository
import com.kareem.appusergithub.data.model.UserItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainViewModel(private val repository: Repository) : ViewModel() {
    fun searchUser(query:String) = repository.getSearch(query)
}