package com.verindrarizya.attendancefirebase.ui.screens.authentication.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verindrarizya.attendancefirebase.data.repository.AuthRepository
import com.verindrarizya.attendancefirebase.util.ResourceState
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

    private val _registerResourceState: MutableStateFlow<ResourceState<String>> =
        MutableStateFlow(ResourceState.Init)
    val registerResourceState = _registerResourceState.asStateFlow()

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
        registerUiState.value.apply {
            viewModelScope.launch {
                authRepository.register(email, password, fullName).collect {
                    if (it is ResourceState.Error) {
                        _message.emit(it.message)
                    }
                    _registerResourceState.value = it
                }
            }
        }
    }

}