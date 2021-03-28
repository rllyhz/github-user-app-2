package id.rllyhz.githubuserapp.ui.user_detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FollowingFollowersPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> Fragment()
            1 -> Fragment()
            else -> Fragment()
        }

    override fun getItemCount(): Int =
        UserDetailActivity.TAB_TITLES.size
}