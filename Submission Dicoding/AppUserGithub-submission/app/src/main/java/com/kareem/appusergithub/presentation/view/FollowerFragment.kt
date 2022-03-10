package com.kareem.appusergithub.presentation.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareem.appusergithub.data.ViewModelFactory
import com.kareem.appusergithub.data.remote.UserItems
import com.kareem.appusergithub.presentation.adapter.GithubUserAdapter
import com.kareem.appusergithub.databinding.FragmentFollowersBinding
import com.kareem.appusergithub.presentation.adapter.SectionPagerAdapter.Companion.BUNDLE
import com.kareem.appusergithub.presentation.view.DetailActivity.Companion.DATA_TAG
import com.kareem.appusergithub.presentation.viewModel.MainViewModel
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [FollowersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(BUNDLE)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        mainViewModel.getFollowers(username.toString())
        mainViewModel.follower.observe(viewLifecycleOwner, {response ->
            showFollowerData(response)
        })

        mainViewModel.isLoading.observe(viewLifecycleOwner, { loading ->
            showLoading(loading)
        })

    }

    private fun showLoading(loading: Boolean) {
        binding.progressBarFollower.progressBar.visibility = if(loading) View.VISIBLE else View.GONE
    }

    private fun showFollowerData(response: ArrayList<UserItems>) {
        if (requireContext().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvFollowers.layoutManager = GridLayoutManager(requireContext(), 2)
        } else {
            binding.rvFollowers.layoutManager = LinearLayoutManager(requireContext())
        }
        val userAdapter = GithubUserAdapter(response)
        binding.rvFollowers.adapter = userAdapter

        userAdapter.setOnItemClickCallback(object : GithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItems) {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(DATA_TAG, data)
                startActivity(intent)
            }
        })
    }
}