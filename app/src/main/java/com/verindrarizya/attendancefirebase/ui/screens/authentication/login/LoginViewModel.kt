package com.verindrarizya.attendancefirebase.ui.screens.authentication.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _loginUiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    fun onEmailChanged(newEmailValue: String) {
        _loginUiState.update { it.copy(email = newEmailValue) }
    }

    fun onPasswordChanged(newPasswordValue: String) {
        _loginUiState.update { it.copy(password = newPasswordValue) }
    }

    fun login() {

    }

}