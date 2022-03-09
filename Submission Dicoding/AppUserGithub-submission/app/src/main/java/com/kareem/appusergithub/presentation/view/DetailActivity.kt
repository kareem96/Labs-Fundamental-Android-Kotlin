package com.kareem.appusergithub.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.kareem.appusergithub.presentation.adapter.SectionPagerAdapter
import com.kareem.appusergithub.databinding.ActivityDetailBinding
import com.kareem.appusergithub.utils.Constant.TABS_TITLES

class DetailActivity : AppCompatActivity(){
    private lateinit var binding: ActivityDetailBinding

    companion object{
        const val EXTRA_GITHUB_USER = "extra_github_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        val username = intent.getStringExtra(EXTRA_GITHUB_USER)


        showSectionPagerAdapter(username.toString());
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