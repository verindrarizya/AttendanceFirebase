package com.verindrarizya.attendancefirebase.ui.screens.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verindrarizya.attendancefirebase.data.repository.AuthRepository
import com.verindrarizya.attendancefirebase.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginUiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _loginResourceState: MutableStateFlow<ResourceState<String>> =
        MutableStateFlow(ResourceState.Init)
    val loginResourceState = _loginResourceState.asStateFlow()

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message.asSharedFlow()

    fun onEmailChanged(newEmailValue: String) {
        _loginUiState.update { it.copy(email = newEmailValue) }
    }

    fun onPasswordChanged(newPasswordValue: String) {
        _loginUiState.update { it.copy(password = newPasswordValue) }
    }

    fun login() {
        loginUiState.value.apply {
            viewModelScope.launch {
                authRepository.login(email, password).collect {
                    if (it is ResourceState.Error) {
                        _message.emit(it.message)
                    }
                    _loginResourceState.value = it
                }
            }
        }
    }

}