package id.rllyhz.githubuserapp.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import id.rllyhz.githubuserapp.R
import id.rllyhz.githubuserapp.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!! // this approach is from official documentation

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
    }

    private fun setUI() {
        binding.apply {

            setupInitialUI()

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

            setSearchable(true)
            etExploreSearch.requestFocus()
        }
    }

    private fun searchUsers(query: String) {
        if (query.isEmpty())
            return

        Toast.makeText(context, "Searching...", Toast.LENGTH_SHORT).show()

        binding.apply {
            setSearchable(false)
            etExploreSearch.text.clear()
        }
    }

    private fun setSearchable(state: Boolean) {
        binding.apply {
            etExploreSearch.isEnabled = state
            etExploreSearch.isFocusableInTouchMode = state
        }
    }

    private fun setupFailureUI() {
        binding.apply {
            recyclerviewExplore.visibility = View.GONE
            llIllustrationContainer.visibility = View.GONE
        }
    }

    private fun setupSuccessUI() {
        binding.apply {
            recyclerviewExplore.visibility = View.VISIBLE
            llIllustrationContainer.visibility = View.GONE
        }
    }

    private fun setupInitialUI() {
        binding.apply {
            recyclerviewExplore.visibility = View.GONE
            llIllustrationContainer.visibility = View.VISIBLE

            swipeRefreshExplore.setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.redish_500),
                ContextCompat.getColor(requireContext(), R.color.blue_200),
                ContextCompat.getColor(requireContext(), R.color.blue_500)
            )

            swipeRefreshExplore.setOnRefreshListener {
                swipeRefreshExplore.isRefreshing = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // avoiding memory leaks
    }
}