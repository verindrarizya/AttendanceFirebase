package com.verindrarizya.attendancefirebase.util

sealed class ResourceState<out T> {
    object Init : ResourceState<Nothing>()
    object Loading : ResourceState<Nothing>()
    data class Success<T>(val data: T) : ResourceState<T>()
    data class Error(val message: String) : ResourceState<Nothing>()
}