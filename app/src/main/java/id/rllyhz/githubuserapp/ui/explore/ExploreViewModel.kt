package id.rllyhz.githubuserapp.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.repository.MainRepository
import id.rllyhz.githubuserapp.util.DispacherProvider
import id.rllyhz.githubuserapp.util.Resource
import id.rllyhz.githubuserapp.util.ResourceEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispachers: DispacherProvider
) : ViewModel() {

    private var _state: MutableStateFlow<ResourceEvent> = MutableStateFlow(ResourceEvent.Empty)
    val state: StateFlow<ResourceEvent> = _state

    private var _searchResults: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    val searchResults: LiveData<Resource<List<User>>> = _searchResults

    private var _lastQuery: MutableLiveData<String> = MutableLiveData()
    val lastQuery: LiveData<String> = _lastQuery

    fun setLastQuery(newQuery: String) {
        _lastQuery.value = newQuery
    }

    fun searchUsers(query: String) {
        _state.value = ResourceEvent.Loading
    }
}