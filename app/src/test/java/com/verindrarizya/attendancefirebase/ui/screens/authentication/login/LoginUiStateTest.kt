package com.verindrarizya.attendancefirebase.ui.screens.authentication.login

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginUiStateTest {

    private lateinit var loginUiState: LoginUiState

    @Before
    fun setUp() {
        loginUiState = LoginUiState()
    }

    @Test
    fun loginUiState_initialState_fieldsNotErrorAndButtonDisabled() {
        assertEquals(loginUiState.isEmailError, false)
        assertEquals(loginUiState.isPasswordError, false)
        assertEquals(loginUiState.isLoginButtonEnabled, false)
    }

    @Test
    fun loginUiState_inputEmailFieldsNotMatchEmailPattern_errorReturnTrue() {

        loginUiState = loginUiState.copy(email = "random input")

        assertEquals(loginUiState.isEmailError, true)
    }

    @Test
    fun loginUiState_inputEmailFieldWithProperEmail_errorReturnFalse() {
        loginUiState = loginUiState.copy(email = "indra@gmail.com")

        assertEquals(loginUiState.isEmailError, false)
    }

    @Test
    fun loginUiState_inputPasswordWithLengthLessThan8Char_errorReturnTrue() {
        loginUiState = loginUiState.copy(password = "12345")

        assertEquals(loginUiState.isPasswordError, true)
        assertEquals(loginUiState.isLoginButtonEnabled, false)
    }

    @Test
    fun loginUiState_inputPasswordThenDeleteIt_errorReturnFalse() {
        // input password
        loginUiState = loginUiState.copy(password = "12345678")
        // remove/erase it completely
        loginUiState = loginUiState.copy(password = "")

        assertEquals(loginUiState.isPasswordError, false)
        assertEquals(loginUiState.isLoginButtonEnabled, false)
    }

    @Test
    fun loginUiState_inputProperEmailAndPassword_fieldsErrorFalseAndButtonEnabled() {
        loginUiState = loginUiState.copy(
            email = "indra@gmail.com",
            password = "12345678"
        )

        assertEquals(loginUiState.isEmailError, false)
        assertEquals(loginUiState.isPasswordError, false)
        assertEquals(loginUiState.isLoginButtonEnabled, true)
    }
}