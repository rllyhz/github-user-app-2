package id.rllyhz.githubuserapp.util

sealed class ResourceEvent {
    class Success<T>(result: T) : ResourceEvent()
    class Error(message: String) : ResourceEvent()
    class Loading : ResourceEvent()
    class Empty : ResourceEvent()
}