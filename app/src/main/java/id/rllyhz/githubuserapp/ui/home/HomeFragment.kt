package id.rllyhz.githubuserapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
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
            progressbarHome.visibility = View.VISIBLE

            recyclerviewUsers.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = usersAdapter
            }
        }
    }

    private fun collectUsersStateFlow() {
        binding.apply {
            lifecycleScope.launchWhenStarted {
                viewModel.usersStateFlow.collect { event ->
                    when (event) {
                        is ResourceEvent.Success<*> -> {
                            setProgressBarStatus(false)
                            event.resultList
                        }
                        is ResourceEvent.Failure -> {
                            setProgressBarStatus(false)
                        }
                        is ResourceEvent.Loading -> {
                            setProgressBarStatus(true)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun setProgressBarStatus(state: Boolean) {
        binding.progressbarHome.visibility = when (state) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    override fun onDetailIconClick(user: User) {
        val userDetailIntent = Intent(requireActivity(), UserDetailActivity::class.java).apply {
            //putExtra(UserDetailActivity.USER_EXTRAS, user)
        }

        requireActivity()
            .startActivity(
                userDetailIntent
            )
    }
}