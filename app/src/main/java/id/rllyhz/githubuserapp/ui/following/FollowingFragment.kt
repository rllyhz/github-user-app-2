package id.rllyhz.githubuserapp.ui.following

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.githubuserapp.R
import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.ui.home.UserListAdapter
import id.rllyhz.githubuserapp.ui.user_detail.UserDetailActivity
import id.rllyhz.githubuserapp.util.ResourceEvent
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FollowingFragment(private val user: User) : Fragment(R.layout.fragment_follows),
    UserListAdapter.ItemClickCallback {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressbar: ProgressBar
    private var usersAdapter: UserListAdapter? = null

    private val viewModel: FollowingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        usersAdapter = UserListAdapter()
        usersAdapter?.setOnItemListener(this)

        viewModel.getFollowingOfUser(user.username)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI(view)
        collectFollowingStateFlow()
    }

    override fun onDestroy() {
        super.onDestroy()
        usersAdapter = null
    }

    private fun setupUI(view: View) {
        progressbar = view.findViewById(R.id.progressbar_follows)
        recyclerView = view.findViewById(R.id.recyclerview_following)

        setProgressbarStatus(true)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usersAdapter
        }

        viewModel.followings.observe(requireActivity()) { users ->
            usersAdapter?.submitList(users)
        }
    }

    fun collectFollowingStateFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is ResourceEvent.Success<*> -> {
                        setProgressbarStatus(false)
                    }
                    is ResourceEvent.Failure -> {
                        setProgressbarStatus(false)
                    }
                    is ResourceEvent.Loading -> {
                        setProgressbarStatus(true)
                    }
                    else -> Unit
                }
            }
        }
    }

    fun setProgressbarStatus(state: Boolean) {
        progressbar.visibility = when (state) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    override fun onDetailIconClick(user: User) {
        Intent(requireActivity(), UserDetailActivity::class.java).run {
            putExtra(UserDetailActivity.USER_EXTRAS, user)

            requireActivity().startActivity(this)
        }
    }
}