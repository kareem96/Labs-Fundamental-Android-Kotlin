package com.kareem.appusergithub.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.kareem.appusergithub.presentation.repository.Repository

class FollowingViewModel(application: Application): ViewModel() {
    private val repository = Repository(application)

    fun getFollowing(username:String) = repository.getFollowing(username)
}