package id.rllyhz.githubuserapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rllyhz.githubuserapp.api.GithubApi
import id.rllyhz.githubuserapp.data.model.User
import id.rllyhz.githubuserapp.repository.main.MainRepository
import id.rllyhz.githubuserapp.util.DispacherProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel // this is the different between other class
class HomeViewModel @Inject constructor( // @ViewModelInject is deprecated for latest version
    private val mainRepository: MainRepository,
    private val dispachers: DispacherProvider
) : ViewModel() {
    private val usersLiveData = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = usersLiveData
}