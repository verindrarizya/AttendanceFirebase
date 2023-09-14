package com.verindrarizya.attendancefirebase.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verindrarizya.attendancefirebase.common.state.AuthState
import com.verindrarizya.attendancefirebase.data.repository.auth.AuthRepository
import com.verindrarizya.attendancefirebase.data.repository.preferences.PreferencesRepository
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

    private val isAlreadyOnBoarded = preferencesRepository.isAlreadyOnBoarded()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    val authenticationState: StateFlow<Pair<Boolean, AuthState>?> =
        isAlreadyOnBoarded.combine(authRepository.currentAuthState()) { onBoarded: Boolean?, authState: AuthState ->
            onBoarded?.let {
                Pair(onBoarded, authState)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    fun setUserOnBoarded() {
        preferencesRepository.setOnBoarded()
    }
}