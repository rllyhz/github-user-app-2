package id.rllyhz.githubuserapp.ui.user_detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.githubuserapp.R
import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.databinding.ActivityUserDetailBinding
import id.rllyhz.githubuserapp.util.ResourceEvent
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var mAdapter: FollowingFollowersPagerAdapter

    private val viewModel: UserDetailViewModel by viewModels()

    companion object {
        const val USER_EXTRAS = "USER_EXTRAS"

        val TAB_TITLES = listOf(
            R.string.user_detail_following_label,
            R.string.user_detail_followers_label
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userExtra = intent.getParcelableExtra<User>(USER_EXTRAS)

        if (userExtra != null) {
            viewModel.getUser(userExtra.username)

            setupActionBar()
            setupUI()
        }
    }

    private fun setupUI() {
        binding.apply {

            viewModel.user.observe(this@UserDetailActivity) { user ->

                Glide.with(this@UserDetailActivity)
                    .load(user.avatarUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.bg_placeholder_images))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(sivUserDetailAvatar)

                tvUserDetailFullname.text = user.fullName
                tvUserDetailUsername.text = user.username
                tvUserDetailBio.text = user.bio
                tvUserDetailCompany.text = user.companyName
                tvUserDetailLocation.text = user.location
                tvUserDetailBlog.text = user.blogUrl

                tvUserDetailFollowingFollowersCount.text = resources.getString(
                    R.string.user_detail_following_followers_format,
                    user.followingCount,
                    user.followersCount
                )

                tvUserDetailRepositoryCount.text = resources.getQuantityString(
                    R.plurals.user_detail_repository_format,
                    user.repositoriesCount,
                    user.repositoriesCount
                )

                // also set pager adapter
                mAdapter = FollowingFollowersPagerAdapter(this@UserDetailActivity, user)
                viewPagerUserDetail.adapter = mAdapter

                TabLayoutMediator(tabLayoutUserDetail, viewPagerUserDetail) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }

            lifecycleScope.launchWhenStarted {
                viewModel.state.collect { event ->
                    when (event) {
                        is ResourceEvent.Success<*> -> setProgressBarStatus(false)
                        is ResourceEvent.Failure -> setProgressBarStatus(false)
                        is ResourceEvent.Loading -> setProgressBarStatus(true)
                        else -> setProgressBarStatus(false)
                    }
                }
            }
        }
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = getString(R.string.action_bar_title_user_detail)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_left)
        }
    }

    private fun setProgressBarStatus(state: Boolean) {
        binding.progressbarUserDetail.visibility = when (state) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}