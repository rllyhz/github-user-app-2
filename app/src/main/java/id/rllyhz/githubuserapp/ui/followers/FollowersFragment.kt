package id.rllyhz.githubuserapp.ui.followers

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.githubuserapp.R
import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.ui.home.UserListAdapter
import id.rllyhz.githubuserapp.ui.user_detail.UserDetailActivity

@AndroidEntryPoint
class FollowersFragment(private val user: User) : Fragment(R.layout.fragment_follows), UserListAdapter.ItemClickCallback {
    // for this class, no need view binding
    private lateinit var recyclerView: RecyclerView
    private var usersAdapter: UserListAdapter? = null

    private val viewModel: FollowingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        usersAdapter = UserListAdapter()
        usersAdapter?.setOnItemListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerview(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        usersAdapter = null
    }

    private fun setupRecyclerview(view: View) {
        recyclerView = view.findViewById(R.id.recyclerview_following)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usersAdapter
        }
    }

    override fun onDetailIconClick(user: User) {
        Intent(requireActivity(), UserDetailActivity::class.java).run {
            putExtra(UserDetailActivity.USER_EXTRAS, user)

            requireActivity().startActivity(this)
        }
    }
}