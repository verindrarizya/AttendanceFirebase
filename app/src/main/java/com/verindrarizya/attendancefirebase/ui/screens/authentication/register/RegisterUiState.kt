package com.verindrarizya.attendancefirebase.ui.screens.authentication.register

data class RegisterUiState(
    val email: String = "",
    val fullName: String = "",
    val password: String = "",
    val repeatPassword: String? = null,
) {
    val isRepeatPasswordError: Boolean
        get() = if (repeatPassword.isNullOrEmpty()) false else password != repeatPassword

    val registerEnabled: Boolean
        get() = email.isNotBlank()
                && fullName.isNotBlank()
                && password.isNotBlank()
                && repeatPassword?.isNotBlank() ?: false
                && !isRepeatPasswordError
}
