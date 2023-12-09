package com.verindrarizya.attendancefirebase.ui.screens.dashboard.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verindrarizya.attendancefirebase.core.data.repository.auth.AuthRepository
import com.verindrarizya.attendancefirebase.core.data.repository.profile.ProfileRepository
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
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState = _profileUiState.asStateFlow()

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message.asSharedFlow()

    init {
        getUserData()
        getUserProfile()
    }

    private fun getUserData() {
        val user = profileRepository.getUserData()
        _profileUiState.update {
            it.copy(
                username = user.username,
                email = user.email
            )
        }
    }

    private fun getUserProfile() {
        viewModelScope.launch {
            profileRepository.getProfileData().collect { resourceState ->
                when (resourceState) {
                    is Resource.Error -> {
                        _profileUiState.update { it.copy(isLoading = false, isRefreshing = false) }
                        _message.emit(resourceState.message)
                    }

                    Resource.Init -> { /* Do Nothing */
                    }

                    Resource.Loading -> {
                        _profileUiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
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