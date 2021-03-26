package id.rllyhz.githubuserapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rllyhz.githubuserapp.repository.main.MainRepository
import id.rllyhz.githubuserapp.util.DispacherProvider
import id.rllyhz.githubuserapp.util.Resource
import id.rllyhz.githubuserapp.util.ResourceEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel // this is the different between other class
class HomeViewModel @Inject constructor( // @ViewModelInject is deprecated for latest version
    private val repository: MainRepository,
    private val dispachers: DispacherProvider
) : ViewModel() {

    private val _users = MutableStateFlow<ResourceEvent>(ResourceEvent.Empty)
    val users: StateFlow<ResourceEvent> = _users

    fun getUsers() {
        viewModelScope.launch(dispachers.default) {
            _users.value = ResourceEvent.Loading

            when (val usersResponse = repository.getUsers()) {
                is Resource.Error -> _users.value =
                    ResourceEvent.Failure(usersResponse.message ?: "")
                is Resource.Success -> _users.value =
                    ResourceEvent.Success(usersResponse.data!!, null)
            }
        }
    }
}