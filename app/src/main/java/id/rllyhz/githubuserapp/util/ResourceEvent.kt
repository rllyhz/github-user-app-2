package id.rllyhz.githubuserapp.util

sealed class ResourceEvent {
    class Success<T>(val resultList: List<T>?, val result: T?) : ResourceEvent()
    class Failure(val message: String) : ResourceEvent()
    object Loading : ResourceEvent()
    object Empty : ResourceEvent()
}