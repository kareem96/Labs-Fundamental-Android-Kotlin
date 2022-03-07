package com.kareemdev.latihanrepositorydi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kareemdev.latihanrepositorydi.data.NewsRepository
import com.kareemdev.latihanrepositorydi.data.local.entity.NewsEntity
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel(){
    fun getHeadlineNews() = newsRepository.getHeadlineNews()

    fun getBookmarkedNews() = newsRepository.getBookMarkedNews()

    fun saveNews(news: NewsEntity){
        viewModelScope.launch {
            newsRepository.setBookmarkedNews(news, true)
        }
    }
    fun deleteNews(news: NewsEntity){
        viewModelScope.launch {
            newsRepository.setBookmarkedNews(news, false)
        }
    }
}