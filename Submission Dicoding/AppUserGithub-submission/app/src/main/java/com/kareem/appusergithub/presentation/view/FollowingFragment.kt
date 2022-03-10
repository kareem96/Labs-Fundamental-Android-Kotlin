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
import com.kareem.appusergithub.databinding.FragmentFollowingBinding
import com.kareem.appusergithub.presentation.adapter.SectionPagerAdapter
import com.kareem.appusergithub.presentation.adapter.SectionPagerAdapter.Companion.BUNDLE
import com.kareem.appusergithub.presentation.viewModel.MainViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : Fragment(){
    private lateinit var uAdapter: GithubUserAdapter
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var mainViewModel: MainViewModel

    /*companion object {
        private val ARG_USERNAME = "username"
        fun newInstance(username: String) : FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
            }
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFollowingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(SectionPagerAdapter.BUNDLE)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        mainViewModel.getFollowing(username.toString())

        mainViewModel.following.observe(viewLifecycleOwner){
            listFollowing(it)
        }
    }

    private fun listFollowing(response: ArrayList<UserItems>) {
        if(requireContext().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.rvFollowing.layoutManager = GridLayoutManager(requireContext(), 2)
        }else{
            binding.rvFollowing.layoutManager = LinearLayoutManager(requireContext())
        }
        val uAdapter = GithubUserAdapter(response)
        binding.rvFollowing.adapter = uAdapter
        uAdapter.setOnItemClickCallback(object : GithubUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserItems) {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(BUNDLE, data)
                startActivity(intent)
            }

        })
    }
}