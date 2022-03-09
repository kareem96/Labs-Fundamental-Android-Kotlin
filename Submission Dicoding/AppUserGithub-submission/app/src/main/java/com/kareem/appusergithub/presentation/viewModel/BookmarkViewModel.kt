package com.kareem.appusergithub.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kareem.appusergithub.data.Repository

class BookmarkViewModel(application: Application): AndroidViewModel(application) {
    private val repository = Repository(application)

    suspend fun getBookmarkedList() = repository.getBookmarkedUser()
}