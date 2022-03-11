package com.kareem.appusergithub.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kareem.appusergithub.utils.SettingsMode

class SettingViewModelFactory(private val prefs: SettingsMode) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SettingViewModel::class.java)){
        return SettingViewModel(prefs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}