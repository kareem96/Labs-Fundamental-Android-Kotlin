package com.kareem.appusergithub.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import com.kareem.appusergithub.R
import com.kareem.appusergithub.presentation.viewModel.ViewModelFactory
import com.kareem.appusergithub.data.response.DetailResponse
import com.kareem.appusergithub.presentation.adapter.SectionPagerAdapter
import com.kareem.appusergithub.databinding.ActivityDetailBinding
import com.kareem.appusergithub.presentation.viewModel.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private var isChecked = false
    private lateinit var username: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = obtainFactory(this as AppCompatActivity)

        viewPager()

        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        username = intent.getStringExtra(EXTRA_USERNAME) as String

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = username
        supportActionBar?.elevation = 0f

        detailViewModel.getDetail(username).observe(this){ detail ->
            contentDetails(detail)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.getStarUser(id)
            withContext(Dispatchers.Main){
                isChecked = if (count > 0){
                    isStar(true)
                    true
                }else{
                    isStar(false)
                    false
                }
            }
        }


        binding.fabShare.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(
                Intent.EXTRA_TEXT, "$id"
            )
            startActivity(Intent.createChooser(shareIntent, "Share Github User"))

        }

        binding.fabStar.setOnClickListener {
            isChecked = !isChecked
            if(isChecked){
                detailViewModel.insertStar(id, username, avatarUrl!!)
                Toast.makeText(this, "Berhasil menambahkan Favorite", Toast.LENGTH_SHORT).show()
            }else{
                detailViewModel.deleteStart(id)
                Toast.makeText(this, "Berhasil menghapus Favorite", Toast.LENGTH_SHORT).show()
            }
            isStar(isChecked)
        }

    }

    private fun isStar(condition: Boolean) {
        val buttonFab = binding.fabStar
        if(condition){
            buttonFab.setImageResource(R.drawable.ic_unstar)
        }else{
            buttonFab.setImageResource(R.drawable.ic_star)
        }
    }

    private fun viewPager(){
        val sectionPagerAdapter = listOf(FollowerFragment(), FollowingFragment())
        val viewPager = listOf("Followers","Following")

        binding.viewPager.adapter = SectionPagerAdapter(sectionPagerAdapter, this.supportFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = viewPager[position]
        }.attach()
    }

    private fun contentDetails(detail: DetailResponse) {
        binding.apply {
            name.text = detail.name
            loc.text = detail.location
            txtFollower.text = detail.followers.toString()
            txtFollowing.text = detail.following.toString()
            txtRepository.text = detail.publicRepos.toString()
            imageDetail.load(detail.avatarUrl){
                placeholder(R.drawable.ic_placeholder)
            }
        }
    }

    private fun obtainFactory(activity: AppCompatActivity): DetailViewModel{
        val factory: ViewModelFactory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
//        finish()
        return super.onSupportNavigateUp()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    fun getData(): String = username

    companion object {
//        const val DATA_TAG = "DATA"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_AVATAR = "extra_avatar"
    }

}