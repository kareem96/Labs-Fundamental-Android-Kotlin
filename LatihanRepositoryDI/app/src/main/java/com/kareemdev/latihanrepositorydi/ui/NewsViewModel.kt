package com.kareemdev.latihanrepositorydi.ui

import androidx.lifecycle.ViewModel
import com.kareemdev.latihanrepositorydi.data.NewsRepository
import com.kareemdev.latihanrepositorydi.data.local.entity.NewsEntity

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel(){
    fun getHeadlineNews() = newsRepository.getHeadlineNews()

    fun getBookmarkedNews() = newsRepository.getBookMarkedNews()

    fun saveNews(news: NewsEntity){
        newsRepository.setBookmarkedNews(news, true)
    }
    fun deleteNews(news: NewsEntity){
        newsRepository.setBookmarkedNews(news, false)
    }
}