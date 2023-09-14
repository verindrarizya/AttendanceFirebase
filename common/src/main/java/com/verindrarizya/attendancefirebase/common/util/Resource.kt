package com.verindrarizya.attendancefirebase.common.util

sealed class Resource<out T> {
    object Init : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
}