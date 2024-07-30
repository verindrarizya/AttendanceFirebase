package com.verindrarizya.attendancefirebase.core.util

import androidx.compose.runtime.Stable

@Stable
sealed class Resource<out T> {
    data object Init : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
}