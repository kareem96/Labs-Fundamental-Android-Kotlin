package com.kareem.appusergithub.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kareem.appusergithub.utils.SettingsMode
import kotlinx.coroutines.launch

class SettingViewModel(private val prefs:SettingsMode) : ViewModel(){
    fun getThemeSettings(): LiveData<Boolean>{
        return prefs.getThemeMode().asLiveData()
    }
    fun saveThemeSettings(isDarkModeActive:Boolean){
        viewModelScope.launch {
            prefs.saveThemeSetting(isDarkModeActive)
        }
    }
}