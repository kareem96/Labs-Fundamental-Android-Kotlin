package com.kareem.appusergithub.presentation.view

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareem.appusergithub.BookmarkActivity
import com.kareem.appusergithub.R
import com.kareem.appusergithub.utils.SettingsActivity
import com.kareem.appusergithub.presentation.adapter.GithubUserAdapter
import com.kareem.appusergithub.data.ViewModelFactory
import com.kareem.appusergithub.data.local.room.UserItems
import com.kareem.appusergithub.databinding.ActivityMainBinding
import com.kareem.appusergithub.presentation.view.DetailActivity.Companion.EXTRA_GITHUB_USER
import com.kareem.appusergithub.presentation.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMain.setHasFixedSize(true)

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
                intent.putExtra(EXTRA_GITHUB_USER, data)
                startActivity(intent)
            }
        })
    }

    /*private fun searchGithubUser() {
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    mainViewModel.getDetailUser(query)
                 return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }*/

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