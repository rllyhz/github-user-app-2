package id.rllyhz.githubuserapp.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.rllyhz.githubuserapp.R
import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.ui.followers.FollowersFragment
import id.rllyhz.githubuserapp.ui.following.FollowingFragment

class FollowingFollowersPagerAdapter(activity: AppCompatActivity, private val user: User) :
    FragmentStateAdapter(activity) {

    companion object {
        val TAB_TITLES = listOf(
            R.string.user_detail_followers_label,
            R.string.user_detail_following_label
        )
    }

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FollowersFragment.newInstance(user)
            1 -> FollowingFragment.newInstance(user)
            else -> FollowersFragment.newInstance(user)
        }

    override fun getItemCount(): Int =
        TAB_TITLES.size
}