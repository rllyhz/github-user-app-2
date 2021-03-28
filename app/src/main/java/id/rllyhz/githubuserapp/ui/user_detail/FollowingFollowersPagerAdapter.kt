package id.rllyhz.githubuserapp.ui.user_detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.ui.followers.FollowersFragment
import id.rllyhz.githubuserapp.ui.following.FollowingFragment

class FollowingFollowersPagerAdapter(activity: AppCompatActivity, private val user: User) :
    FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FollowersFragment(user)
            1 -> FollowingFragment(user)
            else -> FollowersFragment(user)
        }

    override fun getItemCount(): Int =
        UserDetailActivity.TAB_TITLES.size
}