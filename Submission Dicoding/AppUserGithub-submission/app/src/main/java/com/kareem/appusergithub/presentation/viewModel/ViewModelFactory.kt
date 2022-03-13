package com.kareem.appusergithub.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(application) as T

            modelClass.isAssignableFrom(DetailViewModel::class.java) ->
                DetailViewModel(application) as T

            modelClass.isAssignableFrom(FollowersViewModel::class.java) ->
                FollowersViewModel(application) as T

            modelClass.isAssignableFrom(FollowingViewModel::class.java) ->
                FollowingViewModel(application) as T

            modelClass.isAssignableFrom(StarViewModel::class.java) ->
                StarViewModel(application) as T

            else -> throw  IllegalArgumentException("Unknown ViewModel class" + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(application)
            }.also { instance = it }
    }
}