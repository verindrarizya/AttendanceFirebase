package com.verindrarizya.attendancefirebase.ui.screens.dashboard.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.verindrarizya.attendancefirebase.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    init {
        Log.d("ProfileTag", "viewModel: init")
    }

    fun signOut() {
        authRepository.signOut()
    }

    override fun onCleared() {
        Log.d("ProfileTag", "onCleared: called")
        super.onCleared()
    }
}