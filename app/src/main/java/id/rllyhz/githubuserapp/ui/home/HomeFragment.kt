package id.rllyhz.githubuserapp.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.githubuserapp.R
import id.rllyhz.githubuserapp.databinding.FragmentHomeBinding
import id.rllyhz.githubuserapp.util.ResourceEvent
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!! // this approach is from official documentation

    private val viewModel: HomeViewModel by viewModels()

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

        collectUsersStateFlow()

        viewModel.usersLiveData.observe(requireActivity()) { users ->
            Log.d(activity?.packageName, users.toString())
        }

        viewModel.getUsers()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // avoiding memory leaks
    }

    private fun collectUsersStateFlow() {
        binding.apply {
            lifecycleScope.launchWhenStarted {
                viewModel.usersStateFlow.collect { event ->
                    when (event) {
                        is ResourceEvent.Success<*> -> {
                            progressbarHome.visibility = View.GONE
                            event.resultList
                            tvSayFromHome.text = getString(R.string.success_message)
                        }
                        is ResourceEvent.Failure -> {
                            progressbarHome.visibility = View.GONE
                            tvSayFromHome.setTextColor(Color.RED)
                            tvSayFromHome.text = event.message
                        }
                        is ResourceEvent.Loading -> {
                            progressbarHome.visibility = View.VISIBLE
                            tvSayFromHome.text = ""
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}