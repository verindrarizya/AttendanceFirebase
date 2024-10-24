package com.verindrarizya.attendancefirebase.ui.screens.authentication.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verindrarizya.attendancefirebase.core.data.repository.auth.AuthRepository
import com.verindrarizya.attendancefirebase.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _registerUiState: MutableStateFlow<RegisterUiState> =
        MutableStateFlow(RegisterUiState())
    val registerUiState: StateFlow<RegisterUiState> = _registerUiState.asStateFlow()

    private val _registerResource: MutableStateFlow<Resource<String>> =
        MutableStateFlow(Resource.Init)
    val registerResourceState = _registerResource.asStateFlow()

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message.asSharedFlow()

    fun onEmailValueChange(newEmail: String) {
        _registerUiState.update {
            it.copy(email = newEmail)
        }
    }

    fun onFullNameValueChange(newFullName: String) {
        _registerUiState.update {
            it.copy(fullName = newFullName)
        }
    }

    fun onPasswordValueChange(newPassword: String) {
        _registerUiState.update {
            it.copy(password = newPassword)
        }
    }

    fun onRepeatPasswordValueChange(newRepeatPassword: String) {
        _registerUiState.update {
            it.copy(repeatPassword = newRepeatPassword)
        }
    }

    fun register() {
        viewModelScope.launch {
            authRepository.register(
                registerUiState.value.email,
                registerUiState.value.password,
                registerUiState.value.fullName
            ).collect {
                if (it is Resource.Error) {
                    _message.emit(it.message)
                }
                _registerResource.value = it
            }
        }
    }

}