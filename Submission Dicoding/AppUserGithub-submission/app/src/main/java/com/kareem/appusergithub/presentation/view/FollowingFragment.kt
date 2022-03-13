package com.kareem.appusergithub.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareem.appusergithub.databinding.FragmentFollowingBinding
import com.kareem.appusergithub.presentation.adapter.GithubUserAdapter
import com.kareem.appusergithub.presentation.viewModel.FollowingViewModel
import com.kareem.appusergithub.presentation.viewModel.ViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var detailActivity: DetailActivity
    private lateinit var username: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailActivity = activity as DetailActivity
        username = detailActivity.getData()

        val uAdapter = GithubUserAdapter()
        binding?.rvFollowing?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = uAdapter
        }

        showLoading(true)
        showTextDummy(false)
        followingViewModel = obtainFactory(context as AppCompatActivity)
        followingViewModel.getFollowing(username).observe(viewLifecycleOwner){ followingList ->
            if(followingList != null && followingList.isNotEmpty()){
                showLoading(false)
                showTextDummy(false)
                uAdapter.setData(followingList)
            }else{
                showLoading(false)
                showTextDummy(true)

            }
        }
    }

    private fun showTextDummy(state: Boolean) {
        binding?.dummyFollowing?.isVisible = state
    }
    private fun obtainFactory(activity: AppCompatActivity): FollowingViewModel {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(this, factory)[FollowingViewModel::class.java]
    }

    private fun showLoading(loading: Boolean) {
        binding?.progressbarFollowing?.visibility = if (loading) View.VISIBLE else View.GONE
    }

}