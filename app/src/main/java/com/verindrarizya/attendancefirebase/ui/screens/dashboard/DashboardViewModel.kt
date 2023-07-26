package com.verindrarizya.attendancefirebase.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import com.verindrarizya.attendancefirebase.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun signOut() {
        authRepository.signOut()
    }

}