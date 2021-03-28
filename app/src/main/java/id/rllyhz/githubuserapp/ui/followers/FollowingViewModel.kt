package id.rllyhz.githubuserapp.ui.followers

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rllyhz.githubuserapp.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun getFollowingOfUser(username: String) {
        //
    }
}