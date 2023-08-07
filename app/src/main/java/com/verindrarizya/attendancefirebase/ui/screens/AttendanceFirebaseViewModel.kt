package com.verindrarizya.attendancefirebase.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verindrarizya.attendancefirebase.data.repository.AuthRepository
import com.verindrarizya.attendancefirebase.data.repository.PreferencesRepository
import com.verindrarizya.attendancefirebase.util.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AttendanceFirebaseViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    authRepository: AuthRepository
) : ViewModel() {

    private val isUserAlreadyOnBoarded = preferencesRepository.isUserAlreadyOnBoarded
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    val authenticationState: StateFlow<Pair<Boolean, AuthState>?> =
        isUserAlreadyOnBoarded.combine(authRepository.authStateFlow) { onBoarded: Boolean?, authState: AuthState ->
            onBoarded?.let {
                Pair(onBoarded, authState)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    fun setUserOnBoarded() {
        preferencesRepository.setUserOnBoarded()
    }
}