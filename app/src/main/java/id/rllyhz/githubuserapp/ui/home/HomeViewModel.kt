package id.rllyhz.githubuserapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rllyhz.githubuserapp.api.GithubApi
import id.rllyhz.githubuserapp.data.model.User
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel // this is the different between other class
class HomeViewModel @Inject constructor( // @ViewModelInject is deprecated for latest version
    api: GithubApi
) : ViewModel() {
    private val usersLiveData = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = usersLiveData

    init {
        viewModelScope.launch {
            val usersResponse = api.getUsers()
            val allUser = mutableListOf<User>()

            for (userResponse in usersResponse) {
                userResponse.apply {
                    allUser.add(
                        User(
                            id = id,
                            username = username,
                            avatarUrl = avatar_url
                        )
                    )
                }
            }

            usersLiveData.value = allUser
        }
    }
}