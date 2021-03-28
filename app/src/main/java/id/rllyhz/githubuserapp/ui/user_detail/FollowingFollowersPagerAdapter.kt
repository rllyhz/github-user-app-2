package id.rllyhz.githubuserapp.ui.user_detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.rllyhz.githubuserapp.ui.followers.FollowersFragment
import id.rllyhz.githubuserapp.ui.following.FollowingFragment

class FollowingFollowersPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment =
        when (position) {
            1 -> FollowersFragment()
            else -> FollowingFragment()
        }

    override fun getItemCount(): Int =
        UserDetailActivity.TAB_TITLES.size
}