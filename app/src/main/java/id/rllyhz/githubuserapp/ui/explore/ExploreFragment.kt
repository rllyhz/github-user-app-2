package id.rllyhz.githubuserapp.ui.explore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.githubuserapp.R
import id.rllyhz.githubuserapp.adapter.UserListAdapter
import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.databinding.FragmentExploreBinding
import id.rllyhz.githubuserapp.ui.user_detail.UserDetailActivity
import id.rllyhz.githubuserapp.util.ResourceEvent
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ExploreFragment : Fragment(), UserListAdapter.ItemClickCallback {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!! // this approach is from official documentation

    private val viewModel: ExploreViewModel by viewModels()

    private var userListAdapter: UserListAdapter? = null
    private var lastQuery: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        userListAdapter = UserListAdapter()
        userListAdapter?.setOnItemListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUI()
        collectState()
    }

    private fun setUI() {
        binding.apply {

            setupInitialUI()

            viewModel.lastQuery.observe(requireActivity()) { query ->
                lastQuery = query
            }

            viewModel.searchResults.observe(requireActivity()) { users ->
                if (users.isEmpty())
                    setupListEmptyUI()
                else {
                    userListAdapter?.submitList(users)
                    setupSuccessUI()
                }
            }
        }
    }

    private fun setupListEmptyUI() {
        binding.apply {
            tvExploreNotFoundFeedback.visibility = View.VISIBLE
            tvExploreUserListLabel.visibility = View.GONE
            recyclerviewExplore.visibility = View.GONE
            llIllustrationContainer.visibility = View.GONE
        }
    }

    private fun setupFailureUI() {
        binding.apply {
            tvExploreUserListLabel.visibility = View.GONE
            recyclerviewExplore.visibility = View.GONE
            llIllustrationContainer.visibility = View.GONE
            tvExploreNotFoundFeedback.visibility = View.GONE
            tvExploreErrorMessage.visibility = View.VISIBLE
            tvExploreErrorMessageDescription.visibility = View.VISIBLE
        }
    }

    private fun setupSuccessUI() {
        binding.apply {
            tvExploreUserListLabel.visibility = View.VISIBLE
            recyclerviewExplore.visibility = View.VISIBLE
            llIllustrationContainer.visibility = View.GONE
            tvExploreNotFoundFeedback.visibility = View.GONE
            tvExploreErrorMessage.visibility = View.GONE
            tvExploreErrorMessageDescription.visibility = View.GONE
        }
    }

    private fun setupInitialUI() {
        binding.apply {

            etExploreSearch.setOnEditorActionListener { etSearchView, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        searchUsers(etSearchView.text.toString())
                        true
                    }
                    else -> false
                }
            }

            mcardExploreSearchIconContainer.setOnClickListener {
                searchUsers(etExploreSearch.text.toString())
            }

            recyclerviewExplore.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = userListAdapter
            }

            swipeRefreshExplore.setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.redish_500),
                ContextCompat.getColor(requireContext(), R.color.blue_200),
                ContextCompat.getColor(requireContext(), R.color.blue_500)
            )

            recyclerviewExplore.visibility = View.GONE
            llIllustrationContainer.visibility = View.VISIBLE
            tvExploreErrorMessage.visibility = View.GONE
            tvExploreErrorMessageDescription.visibility = View.GONE

            swipeRefreshExplore.setOnRefreshListener {
                searchUsers(lastQuery)
            }
        }
    }

    private fun searchUsers(query: String) {
        showSwipeRefreshLayout(false)
        setSearchable(true)

        if (query.isEmpty()) {
            return
        }

        viewModel.searchUsers(query)
    }

    private fun setSearchable(state: Boolean) {
        binding.apply {
            etExploreSearch.isEnabled = state
            etExploreSearch.isFocusableInTouchMode = state

            if (state)
                etExploreSearch.requestFocus()
            else
                etExploreSearch.clearFocus()
        }
    }

    private fun showSwipeRefreshLayout(state: Boolean) {
        binding.swipeRefreshExplore.isRefreshing = state
    }

    private fun collectState() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is ResourceEvent.Success<*> -> {
                        showSwipeRefreshLayout(false)
                        setupSuccessUI()
                        setSearchable(true)
                    }
                    is ResourceEvent.Failure -> {
                        showSwipeRefreshLayout(false)
                        setupFailureUI()
                        setSearchable(true)
                    }
                    is ResourceEvent.Loading -> {
                        showSwipeRefreshLayout(true)
                        setSearchable(false)
                        binding.tvExploreNotFoundFeedback.visibility = View.GONE
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onDetailIconClick(user: User) {
        Intent(requireActivity(), UserDetailActivity::class.java).run {
            putExtra(UserDetailActivity.USER_EXTRAS, user)
            requireActivity().startActivity(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // avoiding memory leaks
        userListAdapter = null
    }
}