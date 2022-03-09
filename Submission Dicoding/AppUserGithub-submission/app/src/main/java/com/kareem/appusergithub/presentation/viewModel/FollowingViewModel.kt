package com.kareem.appusergithub.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kareem.appusergithub.data.Repository
import com.kareem.appusergithub.data.model.UserItems

class FollowingViewModel(application: Application) : AndroidViewModel(application){
    private val repository = Repository(application)

    fun getFollowing(username:String) = repository.getFollowing(username)
}