package com.kareem.appusergithub.presentation.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareem.appusergithub.data.local.entity.UserEntity
import com.kareem.appusergithub.data.model.UserItems
import com.kareem.appusergithub.databinding.ActivityStarBinding
import com.kareem.appusergithub.presentation.adapter.GithubUserAdapter
import com.kareem.appusergithub.presentation.viewModel.StarViewModel
import com.kareem.appusergithub.presentation.viewModel.ViewModelFactory

class StarActivity : AppCompatActivity() {

    lateinit var binding: ActivityStarBinding
    private lateinit var starViewModel: StarViewModel
    private lateinit var adapter: GithubUserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "List Favorite"

        adapter = GithubUserAdapter()
        binding.rvStar.apply {
            layoutManager = LinearLayoutManager(this@StarActivity)
            setHasFixedSize(true)
            adapter = this@StarActivity.adapter
        }

        starViewModel = obtainFactory(this as AppCompatActivity)
        starViewModel.getStar().observe(this){
            val listStar = mapStarList(it)
            if(it != null && it.isNotEmpty()){
                showTextDummy(false)
                adapter.setData(listStar)
            }else{
                showTextDummy(true)
                adapter.setData(listStar)
            }
        }

    }


    private fun showTextDummy(state: Boolean) {
        binding.tvNoneFavorite.isVisible = state
    }

    private fun mapStarList(starList: List<UserEntity>): ArrayList<UserItems> {
        val list = ArrayList<UserItems>()
        for(user in starList){
            val listMap = UserItems(
                user.id,
                user.login,
                user.avatar_url,
            )
            list.add(listMap)
        }
        return list
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun obtainFactory(activity: AppCompatActivity): StarViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[StarViewModel::class.java]
    }
}