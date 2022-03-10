package com.kareem.appusergithub.presentation.viewModel


import androidx.lifecycle.ViewModel
import com.kareem.appusergithub.presentation.repository.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {

    val isLoading = repository.isLoading
    val searchUser = repository.searchUser
    val detailUser = repository.detailUser
    val follower = repository.follower
    val following = repository.following

    fun getUser(username:String) = repository.getSearch(username)

    fun getDetailUser(username:String) = repository.getDetailUser(username)
    fun getFollowers(username:String) = repository.getFollowers(username)
    fun getFollowing(username:String) = repository.getFollowing(username)

}