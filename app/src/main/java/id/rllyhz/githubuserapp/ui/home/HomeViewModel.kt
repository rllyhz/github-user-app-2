package id.rllyhz.githubuserapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rllyhz.githubuserapp.api.GithubApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel // this is the different between other class
class HomeViewModel @Inject constructor( // @ViewModelInject is deprecated for latest version
    api: GithubApi
) : ViewModel() {

    init {
        viewModelScope.launch {
            val users = api.getUsers()
        }
    }
}