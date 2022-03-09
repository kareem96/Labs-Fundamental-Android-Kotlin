package com.kareem.appusergithub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareem.appusergithub.data.Result
import com.kareem.appusergithub.data.model.UserItems
import com.kareem.appusergithub.databinding.ActivityBookmarkBinding
import com.kareem.appusergithub.databinding.UserItemBinding
import com.kareem.appusergithub.presentation.adapter.GithubUserAdapter
import com.kareem.appusergithub.presentation.viewModel.BookmarkViewModel
import com.kareem.appusergithub.utils.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarkActivity : AppCompatActivity(), ViewStateCallback<List<UserItems>> {

    private val bookmarkViewModel: BookmarkViewModel by viewModels()
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

        uAdapter = GithubUserAdapter()
        binding.rvBookmarked.apply {
            adapter = uAdapter
            layoutManager = LinearLayoutManager(this@BookmarkActivity, LinearLayoutManager.VERTICAL, false)
        }

        CoroutineScope(Dispatchers.Main).launch {
            bookmarkViewModel.getBookmarkedList().observe(this@BookmarkActivity,{
                when(it){
                    is Result.Error -> onFailed(it.message)
                    is Result.Loading -> onLoading()
                    is Result.Success -> it.data?.let { i -> onSuccess(i) }
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onSuccess(data: List<UserItems>) {
        uAdapter.setData(data)
        binding.apply {
            progressBarBookmark.visibility = invisible
        }
    }

    override fun onLoading() {
        binding.apply {
            progressBarBookmark.visibility = visible
        }
    }

    override fun onFailed(message: String?) {
        if(message == null){
            binding.apply {
                progressBarBookmark.visibility = invisible
                tvDummyBookmark.text = "Not found user bookmark"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            bookmarkViewModel.getBookmarkedList().observe(this@BookmarkActivity, {
                when(it){
                    is Result.Success -> it.data?.let { i -> onSuccess(i) }
                    is Result.Loading -> onLoading()
                    is Result.Error -> onFailed(it.message)
                }
            })
        }
    }
}