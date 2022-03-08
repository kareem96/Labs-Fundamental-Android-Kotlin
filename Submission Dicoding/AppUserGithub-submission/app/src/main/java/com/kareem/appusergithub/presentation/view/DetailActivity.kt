package com.kareem.appusergithub.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kareem.appusergithub.data.Result
import com.kareem.appusergithub.presentation.adapter.SectionPagerAdapter
import com.kareem.appusergithub.databinding.ActivityDetailBinding
import com.kareem.appusergithub.data.model.UserItems
import com.kareem.appusergithub.presentation.viewModel.MainDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val mainDetailViewModel by viewModels<MainDetailViewModel>()
//    private var githubUser = UserItems() as UserItems?

    companion object{
        const val EXTRA_GITHUB_USER = "extra_github_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0F
        }

        CoroutineScope(Dispatchers.Main).launch {
            mainDetailViewModel.getUser(EXTRA_GITHUB_USER.toString()).observe(this@DetailActivity){
                when(it){
                    is Result.Error -> onFailed(it.message)
                    is Result.Loading -> onLoading()
                    is Result.Success -> onSuccess(it.data)
                }
            }
        }

        /*showDetailsGithubUser()*/
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    private fun onSuccess(data: UserItems?) {
        binding.apply {
            name.text = data?.repository.toString()

            Glide.with(this@DetailActivity)
                .load(data?.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(imageDetail)
        }
    }

    private fun onLoading() {

    }

    private fun onFailed(message: String?) {

    }

    /*private fun showDetailsGithubUser() {
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
    }*/

    private fun showSectionPagerAdapter(username: String) {
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        sectionPagerAdapter.username = username
        binding.viewPager.adapter = sectionPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        supportActionBar?.elevation = 0f
    }
}