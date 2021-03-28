package id.rllyhz.githubuserapp.ui.user_detail

import androidx.fragment.app.Fragment
import id.rllyhz.githubuserapp.databinding.FragmentUserDetailBinding

class UserDetailFragment : Fragment() {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!! // this approach is from official documentation

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}