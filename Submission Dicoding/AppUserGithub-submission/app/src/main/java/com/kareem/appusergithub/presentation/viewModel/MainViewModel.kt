package com.kareem.appusergithub.presentation.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kareem.appusergithub.data.local.room.UserEntity
import com.kareem.appusergithub.presentation.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val isLoading = repository.isLoading
    val searchUser = repository.searchUser
    val detailUser = repository.detailUser
    val follower = repository.follower
    val following = repository.following
    private val _isStar = MutableLiveData<Boolean>()
    val isStar: LiveData<Boolean> = _isStar



    fun insertStar(user:UserEntity){
        viewModelScope.launch {
            repository.insertStar(user)
        }
    }

    fun deleteStart(user :UserEntity){
        viewModelScope.launch {
            repository.deleteStar(user)
        }
    }

    fun getAllStar(): LiveData<List<UserEntity>> = repository.getAllStar()

    fun getStarUser(username: String){
        _isStar.value = false
        viewModelScope.launch {
            _isStar.value = repository.getStarUser(username)
        }
    }

    fun getUser(username:String) = repository.getSearch(username)

    fun getDetailUser(username:String) = repository.getDetailUser(username)
    fun getFollowers(username:String) = repository.getFollowers(username)
    fun getFollowing(username:String) = repository.getFollowing(username)

}