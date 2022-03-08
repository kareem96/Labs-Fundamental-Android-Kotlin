package com.kareem.appusergithub.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kareem.appusergithub.data.Repository

class MainViewModel(application: Application) : AndroidViewModel(application) {
//    fun searchUser(query:String) = repository.getSearch(query)
    private val repository = Repository(application)
    fun searchUser(query:String) = repository.getSearch(query)
}