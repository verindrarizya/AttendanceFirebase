package com.verindrarizya.attendancefirebase.ui.screens.dashboard.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verindrarizya.attendancefirebase.data.repository.AuthRepository
import com.verindrarizya.attendancefirebase.data.repository.ProfileRepository
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
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState = _profileUiState.asStateFlow()

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message.asSharedFlow()

    init {
        getUsername()
        getEmail()
        getUserProfile()
    }

    private fun getUsername() {
        val username = profileRepository.getUsername()
        _profileUiState.update { it.copy(username = username) }
    }

    private fun getEmail() {
        val email = profileRepository.getEmail()
        _profileUiState.update { it.copy(email = email) }
    }

    private fun getUserProfile() {
        viewModelScope.launch {
            profileRepository.getProfileData().collect { resourceState ->
                when (resourceState) {
                    is ResourceState.Error -> {
                        _profileUiState.update { it.copy(isLoading = false, isRefreshing = false) }
                        _message.emit(resourceState.message)
                    }

                    ResourceState.Init -> { /* Do Nothing */
                    }

                    ResourceState.Loading -> {
                        _profileUiState.update { it.copy(isLoading = true) }
                    }

                    is ResourceState.Success -> {
                        _profileUiState.update {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                address = resourceState.data.address,
                                employeeNumber = resourceState.data.employeeNumber,
                                jobTitle = resourceState.data.jobTitle
                            )
                        }
                    }
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _profileUiState.update { it.copy(isRefreshing = true) }
        }
        getUserProfile()
    }

    fun signOut() {
        authRepository.signOut()
    }
}