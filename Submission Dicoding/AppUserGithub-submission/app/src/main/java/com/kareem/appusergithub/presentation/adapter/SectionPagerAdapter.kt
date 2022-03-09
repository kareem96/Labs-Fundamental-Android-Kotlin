package com.kareem.appusergithub.presentation.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kareem.appusergithub.presentation.view.FollowersFragment
import com.kareem.appusergithub.presentation.view.FollowingFragment
import com.kareem.appusergithub.utils.Constant.TABS_TITLES

class SectionPagerAdapter internal constructor(activity: AppCompatActivity, private val username:String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return TABS_TITLES.size
    }

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> fragment = FollowersFragment.newInstance(username)
            1 -> fragment = FollowingFragment.newInstance(username)
        }
        return fragment as Fragment
    }

}