package com.kareem.appusergithub.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.kareem.appusergithub.R
import com.kareem.appusergithub.databinding.ActivityModeBinding
import com.kareem.appusergithub.presentation.viewModel.SettingViewModel
import com.kareem.appusergithub.presentation.viewModel.SettingViewModelFactory
import com.kareem.appusergithub.utils.SettingsMode

class ModeActivity : AppCompatActivity() {

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var  binding: ActivityModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Change Theme"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val settingPreferences = SettingsMode.getInstance(dataStore)
        val settingModel = ViewModelProvider(this, SettingViewModelFactory(settingPreferences)).get(
            SettingViewModel::class.java
        )

        settingModel.getThemeSettings().observe(this, { isDarkModeActive : Boolean ->
            if(isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.changeTheme.isChecked = true
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.changeTheme.isChecked = false
            }
        })

        binding.changeTheme.setOnCheckedChangeListener{ _: CompoundButton?, isChecked: Boolean ->
            settingModel.saveThemeSettings(isChecked)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}