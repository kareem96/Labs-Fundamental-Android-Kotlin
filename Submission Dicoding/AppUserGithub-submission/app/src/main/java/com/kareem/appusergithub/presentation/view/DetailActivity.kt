package com.kareem.appusergithub.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.kareem.appusergithub.R
import com.kareem.appusergithub.data.Result
import com.kareem.appusergithub.presentation.adapter.SectionPagerAdapter
import com.kareem.appusergithub.databinding.ActivityDetailBinding
import com.kareem.appusergithub.data.model.UserItems
import com.kareem.appusergithub.presentation.viewModel.MainDetailViewModel
import com.kareem.appusergithub.utils.Constant.TABS_TITLES
import com.kareem.appusergithub.utils.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity(), ViewStateCallback<UserItems?> {

    private lateinit var binding: ActivityDetailBinding
    private val mainDetailViewModel by viewModels<MainDetailViewModel>()

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

        CoroutineScope(Dispatchers.Main).launch {
            mainDetailViewModel.getUser(username.toString()).observe(this@DetailActivity,{
                when(it){
                    is Result.Error -> onFailed(it.message)
                    is Result.Loading -> onLoading()
                    is Result.Success -> onSuccess(it.data)
                }
            })
        }

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

    override fun onSuccess(data: UserItems?) {
        binding.apply {
            name.text = data?.username.toString()
            txtFollower.text = data?.followers.toString()
            txtFollowing.text = data?.following.toString()
            txtRepository.text = data?.repository.toString()
            loc.text = data?.location.toString()

            supportActionBar?.title = data?.username

            Glide.with(this@DetailActivity)
                .load(data?.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(imageDetail)


            marked.visibility = View.VISIBLE

            if(data?.isBookmarked == true)
                marked.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_added))
            else
                marked.setImageDrawable(resources.getDrawable(R.drawable.ic_unfavorite))

            marked.setOnClickListener {
                if(data?.isBookmarked == true){
                    data.isBookmarked = false
                    mainDetailViewModel.deleteUser(data)
                    marked.setImageDrawable(resources.getDrawable(R.drawable.ic_unfavorite))
                }else{
                    data?.isBookmarked = true
                    data?.let { i -> mainDetailViewModel.insertBookmarkedUser(i) }
                    marked.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_added))
                }
            }
        }
    }

    override fun onLoading() {
        binding.marked.visibility = View.INVISIBLE
    }

    override fun onFailed(message: String?) {
        binding.marked.visibility = View.INVISIBLE
    }
}