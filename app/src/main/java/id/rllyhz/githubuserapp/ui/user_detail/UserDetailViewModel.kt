package id.rllyhz.githubuserapp.ui.user_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.repository.MainRepository
import id.rllyhz.githubuserapp.util.DispacherProvider
import id.rllyhz.githubuserapp.util.ResourceEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispachers: DispacherProvider
) : ViewModel() {
    private val _currentUser = MutableLiveData<User>()
    val user: LiveData<User> = _currentUser

    private val _state = MutableStateFlow<ResourceEvent>(ResourceEvent.Empty)
    val state: StateFlow<ResourceEvent> = _state

    fun getUser(userId: Int) {
        viewModelScope.launch(dispachers.default) {
            _state.value = ResourceEvent.Loading
        }
    }
}