package com.kareem.appusergithub.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareem.appusergithub.adapter.GithubUserAdapter
import com.kareem.appusergithub.databinding.FragmentFollowersBinding
import com.kareem.appusergithub.databinding.FragmentFollowingBinding
import com.kareem.appusergithub.viewModel.FollowerViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [FollowersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowersFragment : Fragment() {
    private lateinit var followerViewModel: FollowerViewModel
    private lateinit var adapter: GithubUserAdapter

    private lateinit var binding: FragmentFollowersBinding
    companion object{
        private val ARG_USERNAME = "username"
        fun newInstance(username: String) : FollowersFragment{
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME) as String
        showRecyclerGithubUser(username)
    }

    private fun showRecyclerGithubUser(username: String) {
        adapter = GithubUserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvUser.layoutManager = LinearLayoutManager(activity)
        binding.rvUser.adapter = adapter

        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)
        followerViewModel.setGithubUsers(requireActivity().application, username)

        followerViewModel.getGithubUsers().observe(viewLifecycleOwner, Observer { githubUserItems ->
            if (githubUserItems !== null){
                adapter.setData(githubUserItems)
                showLoading(false)
            }else{
                adapter.setData(arrayListOf())
                showLoading(true)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarFollower.visibility = if (state) View.VISIBLE else View.GONE
    }
}