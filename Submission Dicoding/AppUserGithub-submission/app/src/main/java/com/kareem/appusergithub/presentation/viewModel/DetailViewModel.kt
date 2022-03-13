package com.kareem.appusergithub.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.kareem.appusergithub.presentation.repository.Repository

class DetailViewModel(application: Application): ViewModel() {
    private val repository = Repository(application)
    fun getDetail(username: String) = repository.getDetailUser(username)

    fun insertStar(id:Int, username: String, avatarUrl:String) =
        repository.insertStar(id,username, avatarUrl)

    fun deleteStart(id : Int) = repository.deleteStar(id)


    suspend fun getStarUser(id: Int) = repository.getStarUser(id)
}
