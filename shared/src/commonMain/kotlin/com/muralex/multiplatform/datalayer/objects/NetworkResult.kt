package com.muralex.multiplatform.datalayer.objects

sealed class NetworkResult<T> (val body: T?) {
    class Success<T>(body: T) : NetworkResult<T>(body)
    class Failure<T>(val code: Int?, val message: String?) : NetworkResult<T>(null)
}