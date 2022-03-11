package com.kareem.appusergithub.presentation.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kareem.appusergithub.presentation.view.FollowerFragment
import com.kareem.appusergithub.presentation.view.FollowingFragment

class SectionPagerAdapter (activity: AppCompatActivity, val username:String) : FragmentStateAdapter(activity) {
    companion object{
        const val BUNDLE = "username"
    }
    override fun getItemCount(): Int  = 2

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> {
                fragment = FollowerFragment()
                fragment.arguments = Bundle().apply {
                    putString(BUNDLE, username)
                }
            }
            1 ->{
                fragment = FollowingFragment()
                fragment.arguments = Bundle().apply {
                    putString(BUNDLE, username)
                }
            }
        }
        return fragment as Fragment
    }

}