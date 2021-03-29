package id.rllyhz.githubuserapp.ui.followers

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.githubuserapp.R
import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.adapter.UserListAdapter
import id.rllyhz.githubuserapp.ui.user_detail.UserDetailActivity
import id.rllyhz.githubuserapp.util.ResourceEvent
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FollowersFragment(private val user: User) : Fragment(R.layout.fragment_follows),
    UserListAdapter.ItemClickCallback {
    // for this class, no need view binding
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressbar: ProgressBar
    private lateinit var tvFeedbackMessage: TextView
    private var usersAdapter: UserListAdapter? = null

    private val viewModel: FollowersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        usersAdapter = UserListAdapter()
        usersAdapter?.setOnItemListener(this)

        viewModel.getFollowersOfUser(user.username)
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
        tvFeedbackMessage = view.findViewById(R.id.tv_follows_feedback_message)

        setProgressbarStatus(true)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usersAdapter
        }

        viewModel.followers.observe(requireActivity()) { users ->
            usersAdapter?.submitList(users)
        }
    }

    private fun collectFollowingStateFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is ResourceEvent.Success<*> -> {
                        setProgressbarStatus(false)
                        setFeedbackMessageStatus(false)
                    }
                    is ResourceEvent.Failure -> {
                        setProgressbarStatus(false)
                        tvFeedbackMessage.text = resources.getString(R.string.error_message)
                        setFeedbackMessageStatus(true)
                    }
                    is ResourceEvent.Loading -> {
                        setProgressbarStatus(true)
                        setFeedbackMessageStatus(false)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setProgressbarStatus(state: Boolean) {
        progressbar.visibility = when (state) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    private fun setFeedbackMessageStatus(state: Boolean) {
        tvFeedbackMessage.visibility = when (state) {
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