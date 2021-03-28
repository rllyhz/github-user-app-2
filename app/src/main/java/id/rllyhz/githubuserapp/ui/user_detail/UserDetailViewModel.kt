package id.rllyhz.githubuserapp.ui.user_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.rllyhz.githubuserapp.data.model.User

class UserDetailViewModel : ViewModel() {
    private val _currentUser = MutableLiveData<User>()
    val user: LiveData<User> = _currentUser

    fun setUser(user: User) {
        _currentUser.value = user
    }
}