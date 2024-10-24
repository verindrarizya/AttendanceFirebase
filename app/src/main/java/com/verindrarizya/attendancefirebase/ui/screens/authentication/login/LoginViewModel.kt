package com.verindrarizya.attendancefirebase.ui.screens.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verindrarizya.attendancefirebase.core.data.repository.auth.AuthRepository
import com.verindrarizya.attendancefirebase.core.util.Resource
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

    private val _loginResource: MutableStateFlow<Resource<String>> =
        MutableStateFlow(Resource.Init)
    val loginResourceState = _loginResource.asStateFlow()

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message.asSharedFlow()

    fun onEmailChanged(newEmailValue: String) {
        _loginUiState.update { it.copy(email = newEmailValue) }
    }

    fun onPasswordChanged(newPasswordValue: String) {
        _loginUiState.update { it.copy(password = newPasswordValue) }
    }

    fun login() {
        viewModelScope.launch {
            authRepository.login(
                loginUiState.value.email,
                loginUiState.value.password
            ).collect {
                if (it is Resource.Error) {
                    _message.emit(it.message)
                }
                _loginResource.value = it
            }
        }
    }

}