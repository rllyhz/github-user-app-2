package id.rllyhz.githubuserapp.ui.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.repository.MainRepository
import id.rllyhz.githubuserapp.util.DispacherProvider
import id.rllyhz.githubuserapp.util.ResourceEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispachers: DispacherProvider
) : ViewModel() {

    private val _state: MutableStateFlow<ResourceEvent> = MutableStateFlow(ResourceEvent.Empty)
    val state: StateFlow<ResourceEvent> = _state

    private val _following: MutableLiveData<List<User>> = MutableLiveData()
    val followings: LiveData<List<User>> = _following

    fun getFollowersOfUser(username: String) {
        //
    }
}