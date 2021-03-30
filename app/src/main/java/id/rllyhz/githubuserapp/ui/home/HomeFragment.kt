package id.rllyhz.githubuserapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.githubuserapp.R
import id.rllyhz.githubuserapp.adapter.UserListAdapter
import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.databinding.FragmentHomeBinding
import id.rllyhz.githubuserapp.ui.user_detail.UserDetailActivity
import id.rllyhz.githubuserapp.util.ResourceEvent
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : Fragment(), UserListAdapter.ItemClickCallback {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!! // this approach is from official documentation

    private val viewModel: HomeViewModel by viewModels()

    private var usersAdapter: UserListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        usersAdapter = UserListAdapter()
        usersAdapter?.setOnItemListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitialUI()
        collectUsersStateFlow()

        viewModel.usersLiveData.observe(requireActivity()) { users ->
            usersAdapter?.submitList(users)
        }

        viewModel.getUsers()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // avoiding memory leaks
        usersAdapter = null
    }

    private fun setInitialUI() {
        binding.apply {
            swipeRefreshHome.setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.redish_500),
                ContextCompat.getColor(requireContext(), R.color.blue_200),
                ContextCompat.getColor(requireContext(), R.color.blue_500)
            )

            showSwipeRefreshLayout(false)

            recyclerviewUsers.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = usersAdapter
            }

            // swiperefresh
            swipeRefreshHome.setOnRefreshListener {
                viewModel.getUsers()
            }
        }
    }

    private fun collectUsersStateFlow() {
        binding.apply {
            lifecycleScope.launchWhenStarted {
                viewModel.usersStateFlow.collect { event ->
                    when (event) {
                        is ResourceEvent.Success<*> -> {
                            showSwipeRefreshLayout(false)
                            setupSuccessUI()
                        }
                        is ResourceEvent.Failure -> {
                            showSwipeRefreshLayout(false)
                            setupFailureUI()
                        }
                        is ResourceEvent.Loading -> {
                            showSwipeRefreshLayout(true)
                            setupLoadingUI()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun setupLoadingUI() {
        binding.apply {
            tvHomeUserListLabel.visibility = View.GONE
            recyclerviewUsers.visibility = View.GONE
        }
    }

    private fun setupFailureUI() {
        binding.apply {
            mcardHomeHeadlineContainer.visibility = View.GONE
            tvHomeUserListLabel.visibility = View.GONE
            recyclerviewUsers.visibility = View.GONE
            tvHomeErrorMessage.visibility = View.VISIBLE
            tvHomeErrorMessageDescription.visibility = View.VISIBLE
        }
    }

    private fun setupSuccessUI() {
        binding.apply {
            mcardHomeHeadlineContainer.visibility = View.VISIBLE
            tvHomeUserListLabel.visibility = View.VISIBLE
            recyclerviewUsers.visibility = View.VISIBLE
            tvHomeErrorMessage.visibility = View.GONE
            tvHomeErrorMessageDescription.visibility = View.GONE
        }
    }

    private fun showSwipeRefreshLayout(state: Boolean) {
        binding.swipeRefreshHome.isRefreshing = state
    }

    override fun onDetailIconClick(user: User) {
        Intent(requireActivity(), UserDetailActivity::class.java).run {
            putExtra(UserDetailActivity.USER_EXTRAS, user)

            requireActivity().startActivity(this)
        }
    }
}