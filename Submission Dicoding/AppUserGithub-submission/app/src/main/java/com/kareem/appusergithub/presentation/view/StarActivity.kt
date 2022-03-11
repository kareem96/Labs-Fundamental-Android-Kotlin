package com.kareem.appusergithub.presentation.view

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareem.appusergithub.data.ViewModelFactory
import com.kareem.appusergithub.data.local.room.UserEntity
import com.kareem.appusergithub.data.remote.UserItems
import com.kareem.appusergithub.databinding.ActivityStarBinding
import com.kareem.appusergithub.presentation.adapter.StarAdapter
import com.kareem.appusergithub.presentation.view.DetailActivity.Companion.DATA_TAG
import com.kareem.appusergithub.presentation.viewModel.MainViewModel

class StarActivity : AppCompatActivity() {

    lateinit var binding: ActivityStarBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "List Star"

        binding.rvFavorite.setHasFixedSize(true)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@StarActivity)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        mainViewModel.getAllStar().observe(this, { data ->
            shoListStar(data)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun shoListStar(data: List<UserEntity>) {
        if(applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.rvFavorite.layoutManager = GridLayoutManager(this, 2)
        }else{
            binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        }

        val uAdapter = StarAdapter(data)
        binding.rvFavorite.adapter = uAdapter

        uAdapter.setOnItemClickCallback(object : StarAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserEntity) {
                val userItem = UserItems(
                    "","","","", data.username,
                    "", "","","","",data.avatar,
                    "", "",false,0,"","","",

                )
                val intent =  Intent(this@StarActivity, DetailActivity::class.java)
                intent.putExtra(DATA_TAG, userItem)
                startActivity(intent)
            }

        })

    }
}