package com.kareem.appusergithub.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.kareem.appusergithub.data.ViewModelFactory
import com.kareem.appusergithub.data.local.room.UserItems
import com.kareem.appusergithub.data.response.DetailResponse
import com.kareem.appusergithub.presentation.adapter.SectionPagerAdapter
import com.kareem.appusergithub.databinding.ActivityDetailBinding
import com.kareem.appusergithub.presentation.viewModel.MainViewModel
import com.kareem.appusergithub.utils.Constant.TABS_TITLES

class DetailActivity : AppCompatActivity(){
    private lateinit var binding: ActivityDetailBinding
    private lateinit var mainViewModel: MainViewModel

    companion object{
        const val EXTRA_GITHUB_USER = "extra_github_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<UserItems>(EXTRA_GITHUB_USER)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@DetailActivity)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        mainViewModel.getDetailUser(data?.login.toString())

        mainViewModel.detailUser.observe(this,{
            setDetailUser(it)
        })

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        val username = intent.getStringExtra(EXTRA_GITHUB_USER)


        showSectionPagerAdapter(username.toString());
    }

    private fun setDetailUser(detail: DetailResponse?) {
        if (detail != null) {
            binding.name.text = detail.login
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showSectionPagerAdapter(username: String) {
        val pagerAdapter = SectionPagerAdapter(this, username)
        binding.apply {
            viewPager.adapter = pagerAdapter
            TabLayoutMediator(tabs, viewPager){ tabs, position ->
                tabs.text = resources.getString(TABS_TITLES[position])

            }.attach()
        }
    }

}