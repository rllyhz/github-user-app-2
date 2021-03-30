package id.rllyhz.githubuserapp.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
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

            etExploreSearch.requestFocus()
        }
    }

    private fun searchUsers(query: String) {
        if (query.isEmpty())
            return

        Toast.makeText(context, "Searching...", Toast.LENGTH_SHORT).show()

        binding.etExploreSearch.text.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // avoiding memory leaks
    }
}