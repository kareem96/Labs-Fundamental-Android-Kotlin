package com.kareem.appusergithub.data

import androidx.lifecycle.ViewModelProvider

class ViewModelFactory private constructor(private val repository: Repository): ViewModelProvider.NewInstanceFactory(){
    /*override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(repository) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class" + modelClass.name)
    }*/
   /* companion object{
        @Volatile
        private var instance:ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this){
            instance ?: ViewModelFactory(Injection.provideRepository(context))
        }.also { instance = it }
    }*/
}