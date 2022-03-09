package com.kareem.appusergithub.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareem.appusergithub.BookmarkActivity
import com.kareem.appusergithub.R
import com.kareem.appusergithub.utils.SettingsActivity
import com.kareem.appusergithub.presentation.adapter.GithubUserAdapter
import com.kareem.appusergithub.data.Result
import com.kareem.appusergithub.data.model.UserItems
import com.kareem.appusergithub.databinding.ActivityMainBinding
import com.kareem.appusergithub.utils.ViewStateCallback
import com.kareem.appusergithub.presentation.viewModel.MainViewModel

class MainActivity : AppCompatActivity(), ViewStateCallback<List<UserItems>> {
    private lateinit var uQuery:String
    private lateinit var uAdapter: GithubUserAdapter
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRecyclerGithubUser()
        searchGithubUser()
    }

    private fun showRecyclerGithubUser() {
        uAdapter = GithubUserAdapter()
        binding.mainSearch.rvListSearch.apply {
            adapter = uAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun searchGithubUser() {
        binding.searchView.apply {
            queryHint = ""
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    uQuery = query.toString()
                    clearFocus()
                    mainViewModel.searchUser(uQuery).observe(this@MainActivity) {
                        when (it) {
                            is Result.Error -> onFailed(it.message)
                            is Result.Loading -> onLoading()
                            is Result.Success -> it.data?.let { i -> onSuccess(i) }
                        }
                    }
                    return true
                }
                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }
            })
        }
    }

    override fun onSuccess(data: List<UserItems>) {
        uAdapter.setData(data)
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
            R.id.action_mode -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.action_bookmark -> {
                startActivity(Intent(this, BookmarkActivity::class.java))
            }
        }
    }

}