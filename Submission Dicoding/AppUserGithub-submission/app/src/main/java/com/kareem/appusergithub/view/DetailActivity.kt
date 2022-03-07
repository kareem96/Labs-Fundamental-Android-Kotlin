package com.kareem.appusergithub.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kareem.appusergithub.R
import com.kareem.appusergithub.adapter.SectionPagerAdapter
import com.kareem.appusergithub.databinding.ActivityDetailBinding
import com.kareem.appusergithub.model.UserItems
import com.kareem.appusergithub.viewModel.MainDetailViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private lateinit var mainDetailViewModel: MainDetailViewModel
    private var githubUser = UserItems() as UserItems?

    companion object{
        const val EXTRA_GITHUB_USER = "extra_github_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showDetailsGithubUser()
    }

    private fun showDetailsGithubUser() {
        val extraGithubUser = intent.getParcelableExtra(EXTRA_GITHUB_USER) as UserItems

        val actionbar = supportActionBar
        actionbar!!.title = extraGithubUser.username
        actionbar.setDisplayHomeAsUpEnabled(true)

        Glide.with(this)
            .load(extraGithubUser.avatar)
            .into(binding.imageDetail)
        binding.name.text = extraGithubUser.username

        val getUsername = extraGithubUser.username
        mainDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainDetailViewModel::class.java)
        mainDetailViewModel.setGithubUsers(applicationContext, getUsername)

        mainDetailViewModel.getGithubUsers().observe(this, Observer { githubUserItems ->
            binding.txtFollower.text = githubUserItems.followers.toString()
            binding.txtFollowing.text = githubUserItems.following.toString()
            binding.txtRepository.text = githubUserItems.repository.toString()
            binding.loc.text = githubUserItems.location
        })
        showSectionPagerAdapter(getUsername)
    }

    private fun showSectionPagerAdapter(username: String) {
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        sectionPagerAdapter.username = username
        binding.viewPager.adapter = sectionPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        supportActionBar?.elevation = 0f
    }
}