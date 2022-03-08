package com.kareem.appusergithub.presentation.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kareem.appusergithub.R
import com.kareem.appusergithub.presentation.view.FollowersFragment
import com.kareem.appusergithub.presentation.view.FollowingFragment

class SectionPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var username: String? = null

    private val TAB_TITLES = intArrayOf(R.string.follower, R.string.following)


    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowersFragment.newInstance(username.toString())
            1 -> fragment = FollowingFragment.newInstance(username.toString())
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }
}