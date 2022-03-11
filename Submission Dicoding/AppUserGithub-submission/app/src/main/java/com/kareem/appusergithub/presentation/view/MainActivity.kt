package com.kareem.appusergithub.presentation.view

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareem.appusergithub.R
import com.kareem.appusergithub.presentation.adapter.GithubUserAdapter
import com.kareem.appusergithub.data.ViewModelFactory
import com.kareem.appusergithub.data.remote.UserItems
import com.kareem.appusergithub.databinding.ActivityMainBinding
import com.kareem.appusergithub.presentation.view.DetailActivity.Companion.DATA_TAG
import com.kareem.appusergithub.presentation.viewModel.MainViewModel
import com.kareem.appusergithub.presentation.viewModel.SettingViewModel
import com.kareem.appusergithub.presentation.viewModel.SettingViewModelFactory
import com.kareem.appusergithub.utils.SettingsMode


//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private var darkMode : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMain.setHasFixedSize(true)

        themeMode()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@MainActivity)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        mainViewModel.searchUser.observe(this@MainActivity) { response ->
            showRecyclerGithubUser(response)
        }

        mainViewModel.isLoading.observe(this) { loading ->
            showLoading(loading)
        }
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    mainViewModel.getUser(query)
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
//        searchGithubUser()
    }

    private fun themeMode() {
        val prefs = SettingsMode.getInstance(dataStore)
        val settingModel = ViewModelProvider(this, SettingViewModelFactory(prefs)).get(
            SettingViewModel::class.java
        )
        settingModel.getThemeSettings().observe(this,{isDarkModeActive:Boolean ->
            if(isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })
    }

    private fun showLoading(loading: Boolean) {
        binding.progress.progressBar.visibility = if(loading) View.VISIBLE else View.GONE
    }

    private fun showRecyclerGithubUser(response: ArrayList<UserItems>) {
        if(applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.rvMain.layoutManager = GridLayoutManager(this, 2)
        }else{
            binding.rvMain.layoutManager = LinearLayoutManager(this)
        }
        val uAdapter = GithubUserAdapter(response)
        binding.rvMain.adapter = uAdapter
        uAdapter.setOnItemClickCallback(object : GithubUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserItems) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DATA_TAG, data)
                startActivity(intent)
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_setting -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            R.id.action_mode -> {
                startActivity(Intent(this, ModeActivity::class.java))
                return true
            }
            R.id.action_bookmark -> {
                startActivity(Intent(this, StarActivity::class.java))
                return true
            }
            else -> return true
        }

//        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when(selectedMode){
            R.id.action_setting -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.action_mode -> {
                startActivity(Intent(this, ModeActivity::class.java))
            }
            R.id.action_bookmark -> {
                startActivity(Intent(this, StarActivity::class.java))
            }
        }
    }

}