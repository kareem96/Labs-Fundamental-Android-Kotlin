package com.kareem.appusergithub.presentation.viewModel


import android.app.Application
import androidx.lifecycle.ViewModel
import com.kareem.appusergithub.presentation.repository.Repository


class MainViewModel(application:Application) : ViewModel() {
    private val repository = Repository(application)

    fun getSearch(query: String) =  repository.getSearch(query)
}