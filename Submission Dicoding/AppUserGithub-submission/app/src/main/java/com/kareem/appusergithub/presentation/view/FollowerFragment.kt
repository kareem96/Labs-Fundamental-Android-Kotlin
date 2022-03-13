package com.kareem.appusergithub.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareem.appusergithub.presentation.viewModel.ViewModelFactory
import com.kareem.appusergithub.presentation.adapter.GithubUserAdapter
import com.kareem.appusergithub.databinding.FragmentFollowersBinding
import com.kareem.appusergithub.presentation.viewModel.FollowersViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [FollowersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding
    private lateinit var followerViewModel: FollowersViewModel
    private lateinit var detailActivity: DetailActivity
    private lateinit var username: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailActivity = activity as DetailActivity
        username = detailActivity.getData()

        val uAdapter = GithubUserAdapter()
        binding?.rvFollowers?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = uAdapter
        }

        showLoading(true)
        showTextDummy(false)
        followerViewModel = obtainFactory(context as AppCompatActivity)
        followerViewModel.getFollowers(username).observe(viewLifecycleOwner){ followerList ->
            if(followerList != null && followerList.isNotEmpty()){
                showLoading(false)
                showTextDummy(false)
                uAdapter.setData(followerList)
            }else{
                showTextDummy(true)
                showLoading(false)
            }
        }
    }

    private fun obtainFactory(activity: AppCompatActivity): FollowersViewModel{
        val factory: ViewModelFactory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(this, factory)[FollowersViewModel::class.java]
    }

    private fun showTextDummy(state: Boolean) {
        binding?.dummyFollower?.isVisible = state
    }

    private fun showLoading(loading: Boolean) {
        binding?.apply {
            progressbarFollowers.visibility = if (loading) View.VISIBLE else View.GONE
            rvFollowers.isGone = loading
        }
    }
}