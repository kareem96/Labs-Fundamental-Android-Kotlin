package com.kareem.appusergithub.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kareem.appusergithub.data.Repository
import com.kareem.appusergithub.data.model.UserItems
import kotlinx.coroutines.launch

class MainDetailViewModel(application: Application) : AndroidViewModel(application) {
    val repository = Repository(application)

    suspend fun getUser(username:String) = repository.detailUser(username)

    fun insertBookmarkedUser(user:UserItems) = viewModelScope.launch {
        repository.insertBookmarkedUser(user)
    }
    fun deleteUser(user: UserItems) = viewModelScope.launch {
        repository.deleteBookmarkedUser(user)
    }
}