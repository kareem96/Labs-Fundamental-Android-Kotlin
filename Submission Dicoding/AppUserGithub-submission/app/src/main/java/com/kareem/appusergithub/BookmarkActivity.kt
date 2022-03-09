package com.kareem.appusergithub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.kareem.appusergithub.databinding.ActivityBookmarkBinding
import com.kareem.appusergithub.presentation.adapter.GithubUserAdapter

class BookmarkActivity : AppCompatActivity(){

    private lateinit var binding: ActivityBookmarkBinding
    private lateinit var uAdapter: GithubUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Bookmark User"

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}