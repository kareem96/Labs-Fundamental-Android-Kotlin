package com.kareem.appusergithub.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.utils.ViewState
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareem.appusergithub.data.Result
import com.kareem.appusergithub.data.model.UserItems
import com.kareem.appusergithub.presentation.adapter.GithubUserAdapter
import com.kareem.appusergithub.databinding.FragmentFollowersBinding
import com.kareem.appusergithub.presentation.viewModel.FollowerViewModel
import com.kareem.appusergithub.utils.ViewStateCallback

/**
 * A simple [Fragment] subclass.
 * Use the [FollowersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowersFragment : Fragment(), ViewStateCallback<List<UserItems>> {
    private val followerViewModel: FollowerViewModel by viewModels()
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var uAdapter: GithubUserAdapter
    private var username:String? = null

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME) as String
        showRecyclerGithubUser(username)
    }

    private fun showRecyclerGithubUser(username: String) {
        uAdapter = GithubUserAdapter()
        binding.rvFollowers.apply {
            adapter = uAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        followerViewModel.getFollowers(username).observe(viewLifecycleOwner, {
            when(it){
                is Result.Error -> onFailed(it.message)
                is Result.Loading -> onLoading()
                is Result.Success -> it.data?.let{ i -> onSuccess(i) }
            }
        })
    }

    override fun onSuccess(data: List<UserItems>) {
        uAdapter.setData(data)
        binding.apply {
            tvDummyFollower.visibility = invisible
            progressBarFollower.visibility = invisible
            rvFollowers.visibility = visible
        }
    }

    override fun onLoading() {
        binding.apply {
            tvDummyFollower.visibility = invisible
            progressBarFollower.visibility = visible
            rvFollowers.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        binding.apply {
            if(message == null){
                tvDummyFollower.text = ""
                tvDummyFollower.visibility = visible
            }else{
                tvDummyFollower.text = message
                tvDummyFollower.visibility = visible
            }
            progressBarFollower.visibility = invisible
            rvFollowers.visibility = invisible
        }
    }

}