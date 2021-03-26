package id.rllyhz.githubuserapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rllyhz.githubuserapp.repository.main.MainRepository
import id.rllyhz.githubuserapp.util.DispacherProvider
import id.rllyhz.githubuserapp.util.ResourceEvent
import javax.inject.Inject

@HiltViewModel // this is the different between other class
class HomeViewModel @Inject constructor( // @ViewModelInject is deprecated for latest version
    private val mainRepository: MainRepository,
    private val dispachers: DispacherProvider
) : ViewModel() {

    private val _users = MutableLiveData<ResourceEvent>(ResourceEvent.Empty)
    val users: LiveData<ResourceEvent> = _users
}