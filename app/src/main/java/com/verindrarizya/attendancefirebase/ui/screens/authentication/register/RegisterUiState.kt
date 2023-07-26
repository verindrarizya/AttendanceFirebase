package com.verindrarizya.attendancefirebase.ui.screens.authentication.register

import android.util.Patterns

data class RegisterUiState(
    val email: String = "",
    val fullName: String = "",
    val password: String = "",
    val repeatPassword: String? = null,
) {

    val isEmailError: Boolean
        get() = if (email.isBlank()) false else !Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val isPasswordError: Boolean
        get() = if (password.isBlank()) false else password.length < 8

    val isRepeatPasswordError: Boolean
        get() = if (repeatPassword.isNullOrEmpty()) false else password != repeatPassword

    val registerEnabled: Boolean
        get() = email.isNotBlank()
                && fullName.isNotBlank()
                && password.isNotBlank()
                && repeatPassword?.isNotBlank() ?: false
                && !isRepeatPasswordError
}
