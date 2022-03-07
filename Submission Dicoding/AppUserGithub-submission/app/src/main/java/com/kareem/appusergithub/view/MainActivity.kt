package com.kareem.appusergithub.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareem.appusergithub.R
import com.kareem.appusergithub.SettingsActivity
import com.kareem.appusergithub.adapter.GithubUserAdapter
import com.kareem.appusergithub.databinding.ActivityDetailBinding
import com.kareem.appusergithub.databinding.ActivityMainBinding
import com.kareem.appusergithub.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: GithubUserAdapter
    private lateinit var mainViewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRecyclerGithubUser()
        searchGithubUser()
    }

    private fun showRecyclerGithubUser() {
        adapter = GithubUserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvListUser.layoutManager = LinearLayoutManager(this)
        binding.rvListUser.adapter = adapter

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.setGithubUser(applicationContext)

        mainViewModel.getGithubUsers().observe(this, Observer { githubUserItems ->
            if (githubUserItems !== null){
                adapter.setData(githubUserItems)

                showLoading(false)
            }else{
                adapter.setData(arrayListOf())
                showLoading(true)
            }
        })
    }

    private fun searchGithubUser() {
        binding.search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.searchGithubUsers(applicationContext, query)

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when(selectedMode){
            R.id.action_setting -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
    }

}