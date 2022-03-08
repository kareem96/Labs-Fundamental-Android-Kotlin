package com.kareem.appusergithub.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareem.appusergithub.R
import com.kareem.appusergithub.utils.SettingsActivity
import com.kareem.appusergithub.adapter.GithubUserAdapter
import com.kareem.appusergithub.data.Result
import com.kareem.appusergithub.data.model.UserItems
import com.kareem.appusergithub.databinding.ActivityMainBinding
import com.kareem.appusergithub.utils.ViewStateCallback
import com.kareem.appusergithub.viewModel.MainViewModel

class MainActivity : AppCompatActivity(), ViewStateCallback<List<UserItems>> {
    private lateinit var uQuery:String
    private lateinit var adapter: GithubUserAdapter
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        showRecyclerGithubUser()
        adapter = GithubUserAdapter()
        binding.mainSearch.rvListSearch.apply {
            adapter = adapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
//        searchGithubUser()
        binding.searchView.apply {
            queryHint = ""
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    uQuery = query.toString()
                    clearFocus()
                    mainViewModel.searchUser(uQuery).observe(this@MainActivity, {
                        when(it){
                            is Result.Error -> onFailed(it.message)
                            is Result.Loading -> onLoading()
                            is Result.Success -> it.data?.let { i -> onSuccess(i) }
                        }
                    })
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

            })
        }
    }

    private fun showRecyclerGithubUser() {
        /*adapter = GithubUserAdapter()
        binding.mainSearch.rvListSearch.apply {
            adapter = adapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }*/

        /*adapter.notifyDataSetChanged()
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
        })*/
    }

    private fun searchGithubUser() {
        /*binding.searchView.apply {
            queryHint = ""
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    uQuery = query.toString()
                    clearFocus()
                    mainViewModel.searchUser(uQuery).observe(this@MainActivity, {
                        when(it){
                            is Result.Error -> onFailed(it.message)
                            is Result.Loading -> onLoading()
                            is Result.Success -> it.data?.let { i -> onSuccess(i) }
                        }
                    })
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

            })
        }*/
        /*binding.search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.searchGithubUsers(applicationContext, query)

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })*/
    }

    override fun onSuccess(data: List<UserItems>) {
        adapter.setData(data)
        binding.mainSearch.apply {
            ivSearch.visibility = invisible
            tvMessage.visibility = invisible
            progressSearch.visibility = invisible
            rvListSearch.visibility = visible
        }
    }

    override fun onLoading() {
        binding.mainSearch.apply {
            ivSearch.visibility = invisible
            tvMessage.visibility = invisible
            progressSearch.visibility = visible
            rvListSearch.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        binding.mainSearch.apply {
            if(message == null){
                ivSearch.apply {
                    setImageResource(R.drawable.ic_search)
                    visibility = visible
                }
                tvMessage.apply {
                    text = ""
                    visibility = visible
                }
            }else{
                ivSearch.apply {
                    setImageResource(R.drawable.ic_search_off)
                    visibility = visible
                }
                tvMessage.apply {
                    text = message
                    visibility = visible
                }
            }
            progressSearch.visibility = invisible
            rvListSearch.visibility = invisible
        }
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