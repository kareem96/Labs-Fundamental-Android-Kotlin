package com.kareem.appusergithub.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kareem.appusergithub.R
import com.kareem.appusergithub.data.ViewModelFactory
import com.kareem.appusergithub.data.local.room.UserEntity
import com.kareem.appusergithub.data.remote.UserItems
import com.kareem.appusergithub.data.remote.DetailResponse
import com.kareem.appusergithub.presentation.adapter.SectionPagerAdapter
import com.kareem.appusergithub.databinding.ActivityDetailBinding
import com.kareem.appusergithub.presentation.view.DetailActivity.Companion.DATA_TAG
import com.kareem.appusergithub.presentation.viewModel.MainViewModel
import com.kareem.appusergithub.utils.Constant.TABS_TITLES

class DetailActivity : AppCompatActivity(){
    lateinit var binding: ActivityDetailBinding
    private lateinit var mainViewModel: MainViewModel
    private var isFavorited: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<UserItems>(DATA_TAG)

        val sectionPagerAdapter = SectionPagerAdapter(this, data?.login.toString())
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@DetailActivity)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        mainViewModel.getDetailUser(data?.login.toString())

        mainViewModel.detailUser.observe(this, { detail ->
            setDetailUser(detail)
        })

        mainViewModel.isLoading.observe(this, { loading ->
            showLoading(loading)
        })

        mainViewModel.getStarUser(data?.login.toString())

        binding.fabShare.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(
                Intent.EXTRA_TEXT,"$data"
            )
            startActivity(Intent.createChooser(shareIntent,"Share Github User"))

        }

        binding.fabStar.setOnClickListener {
            val mUser = UserEntity(
                data?.login.toString(),
                data?.avatarUrl.toString(),
                true
            )
            if (!isFavorited) {
                mainViewModel.insertStar(mUser)
                Toast.makeText(
                    applicationContext,
                    R.string.star,
                    Toast.LENGTH_SHORT
                ).show()
                mainViewModel.getStarUser(data?.login.toString())
            } else {
                mainViewModel.deleteStart(mUser)
                Toast.makeText(
                    applicationContext,
                    R.string.unstar,
                    Toast.LENGTH_SHORT
                ).show()
                mainViewModel.getStarUser(data?.login.toString())
            }
        }

        mainViewModel.isStar.observe(this, { favorited ->
            isFavorited = if (favorited) {
                binding.fabStar.setImageResource(R.drawable.ic_unstar)
                true
            } else {
                binding.fabStar.setImageResource(R.drawable.ic_star)
                false
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = data?.login.toString()
        supportActionBar?.elevation = 0f

    }

    private fun showLoading(loading: Boolean) {
        binding.progressBar.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun setDetailUser(detail: DetailResponse) {
        binding.name.text = detail.login
        binding.name.text = detail.name
        binding.txtRepository.text = detail.publicRepos.toString()
        binding.loc.text = detail.location
        binding.txtFollower.text = detail.followers.toString()
        binding.txtFollowing.text = detail.following.toString()

        Glide.with(this)
            .load(detail.avatarUrl)
            .circleCrop()
            .into(binding.imageDetail)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }


    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )

        const val DATA_TAG = "DATA"
    }

}