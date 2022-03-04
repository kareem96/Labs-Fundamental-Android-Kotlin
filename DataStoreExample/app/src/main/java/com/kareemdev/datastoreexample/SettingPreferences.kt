package com.kareemdev.datastoreexample

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



class SettingPreferences private constructor(private val dataSource: DataStore<Preferences>){

    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataSource.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean){
        dataSource.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }


    companion object{
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataSource: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this){
                val instance = SettingPreferences(dataSource)
                INSTANCE = instance
                instance
            }
        }
    }
}
