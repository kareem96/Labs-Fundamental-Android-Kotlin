package com.kareem.appusergithub.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareem.appusergithub.R
import com.kareem.appusergithub.adapter.GithubUserAdapter
import com.kareem.appusergithub.databinding.ActivityDetailBinding
import com.kareem.appusergithub.databinding.FragmentFollowingBinding
import com.kareem.appusergithub.viewModel.FollowerViewModel
import com.kareem.appusergithub.viewModel.FollowingViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : Fragment() {
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var adapter: GithubUserAdapter

    private lateinit var binding: FragmentFollowingBinding

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME) as String
        showRecyclerGithubUsers(username)
    }

    private fun showRecyclerGithubUsers(username: String) {
        adapter = GithubUserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvUser.layoutManager = LinearLayoutManager(activity)
        binding.rvUser.adapter = adapter

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        followingViewModel.setGithubUsers(requireActivity().application, username)

        followingViewModel.getGithubUsers().observe(viewLifecycleOwner, Observer { githubUseritems ->
            if (githubUseritems !== null){
                adapter.setData(githubUseritems)
                showLoading(false)
            }else{
                adapter.setData(arrayListOf())
                showLoading(true)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        binding.progressbarFollowing.visibility = if (state) View.VISIBLE else View.GONE
    }

}