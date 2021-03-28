package id.rllyhz.githubuserapp.util

sealed class ResourceEvent {
    class Success<ResulType>(val resultList: List<ResulType>?, val result: ResulType?) : ResourceEvent()
    class Failure(val message: String) : ResourceEvent()
    object Loading : ResourceEvent()
    object Empty : ResourceEvent()
}