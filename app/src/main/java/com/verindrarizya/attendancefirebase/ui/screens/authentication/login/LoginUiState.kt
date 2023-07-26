package com.verindrarizya.attendancefirebase.ui.screens.authentication.login

import android.util.Patterns

data class LoginUiState(
    val email: String = "",
    val password: String = ""
) {

    val isEmailError: Boolean
        get() = if (email.isBlank()) false else !Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val isPasswordError: Boolean
        get() = if (password.isBlank()) false else password.length < 8

    val isLoginButtonEnabled: Boolean
        get() = !isEmailError && !isPasswordError && email.isNotBlank() && password.isNotBlank()
}