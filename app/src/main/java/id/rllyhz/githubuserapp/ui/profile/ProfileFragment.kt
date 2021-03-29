package id.rllyhz.githubuserapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.githubuserapp.adapter.FollowingFollowersPagerAdapter
import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.databinding.FragmentProfileBinding

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!! // this approach is from official documentation

    private var mAdapter: FollowingFollowersPagerAdapter? = null
    private lateinit var user: User

    private val viwModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUI()
    }

    private fun setUI() {

        mAdapter = FollowingFollowersPagerAdapter(requireActivity() as AppCompatActivity, user)

        binding.apply {


            viewPagerProfile.adapter = mAdapter

            TabLayoutMediator(tabLayoutProfile, viewPagerProfile) { tab, position ->
                tab.text =
                    resources.getString(FollowingFollowersPagerAdapter.TAB_TITLES[position])
            }.attach()

            // SwipeRefreshLayout
            swipeRefreshProfile.setOnRefreshListener {
                //
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // avoiding memory leaks
        mAdapter = null
    }
}