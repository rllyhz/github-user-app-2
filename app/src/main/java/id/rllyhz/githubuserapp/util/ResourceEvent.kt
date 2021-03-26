package id.rllyhz.githubuserapp.util

sealed class ResourceEvent {
    class Success<T>(result: T) : ResourceEvent()
    class Error(message: String) : ResourceEvent()
    object Loading : ResourceEvent()
    object Empty : ResourceEvent()
}