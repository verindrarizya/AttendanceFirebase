package com.verindrarizya.attendancefirebase.ui.screens.authentication.register

data class RegisterUiState(
    val email: String = "",
    val fullName: String = "",
    val password: String = "",
    val repeatPassword: String? = null,
) {
    val isRepeatPasswordError: Boolean
        get() = !repeatPassword.isNullOrEmpty() && password != repeatPassword
}
