package com.kareem.appusergithub.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareem.appusergithub.data.Result
import com.kareem.appusergithub.data.model.UserItems
import com.kareem.appusergithub.presentation.adapter.GithubUserAdapter
import com.kareem.appusergithub.databinding.FragmentFollowingBinding
import com.kareem.appusergithub.presentation.viewModel.FollowingViewModel
import com.kareem.appusergithub.utils.ViewStateCallback

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : Fragment(), ViewStateCallback<List<UserItems>> {
    private val followingViewModel: FollowingViewModel by viewModels()
    private lateinit var uAdapter: GithubUserAdapter
    private lateinit var binding: FragmentFollowingBinding
    private var username:String? = null

    companion object {
        private val ARG_USERNAME = "username"
        fun newInstance(username: String) : FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFollowingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME) as String
        showRecyclerGithubUsers(username)
    }

    private fun showRecyclerGithubUsers(username: String) {
        uAdapter = GithubUserAdapter()
        binding.rvFollowing.apply {
            adapter = uAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        followingViewModel.getFollowing(username).observe(viewLifecycleOwner,{
            when(it){
                is Result.Error -> onFailed(it.message)
                is Result.Loading -> onLoading()
                is Result.Success -> it.data?.let { i -> onSuccess(i) }
            }
        })
    }

    override fun onSuccess(data: List<UserItems>) {
        uAdapter.setData(data)
        binding.apply {
            tvDummyFollowing.visibility = invisible
            progressbarFollowing.visibility = invisible
            rvFollowing.visibility = visible
        }
    }

    override fun onFailed(message: String?) {
        binding.apply {
            if(message == null){
                tvDummyFollowing.text = ""
                tvDummyFollowing.visibility = visible
            }else{
                tvDummyFollowing.text = message
                tvDummyFollowing.visibility = visible
            }
            progressbarFollowing.visibility = invisible
            rvFollowing.visibility = invisible
        }
    }

    override fun onLoading() {
        binding.apply {
            tvDummyFollowing.visibility = invisible
            progressbarFollowing.visibility = visible
            rvFollowing.visibility = invisible
        }
    }



}