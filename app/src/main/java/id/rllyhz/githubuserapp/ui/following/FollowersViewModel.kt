package id.rllyhz.githubuserapp.ui.following

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rllyhz.githubuserapp.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun getFollowersOfUser(username: String) {
        //
    }
}