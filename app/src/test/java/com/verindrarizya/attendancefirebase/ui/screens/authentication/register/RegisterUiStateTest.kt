package com.verindrarizya.attendancefirebase.ui.screens.authentication.register

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterUiStateTest {

    private lateinit var registerUiState: RegisterUiState

    @Before
    fun setup() {
        registerUiState = RegisterUiState()
    }

    @Test
    fun registerUiState_initialState_fieldsNotErrorButtonDisabled() {
        assertEquals(registerUiState.isEmailError, false)
        assertEquals(registerUiState.isPasswordError, false)
        assertEquals(registerUiState.isRepeatPasswordError, false)
        assertEquals(registerUiState.registerEnabled, false)
    }

    @Test
    fun registerUiState_inputProperEmail_returnFalseForField() {
        registerUiState = registerUiState.copy(
            email = "indra@gmail.com"
        )

        assertEquals(registerUiState.isEmailError, false)
    }

    @Test
    fun registerUiState_inputNotProperEmail_returnTrueForField() {
        registerUiState = registerUiState.copy(
            email = "not proper format"
        )

        assertEquals(registerUiState.isEmailError, true)
    }

    @Test
    fun registerUiState_inputEmailThenDelete_returnFalseForField() {
        registerUiState = registerUiState.copy(
            email = "not proper"
        )
        registerUiState = registerUiState.copy(
            email = ""
        )

        assertEquals(registerUiState.isEmailError, false)
    }

    @Test
    fun registerUiState_inputPasswordProper_returnFalseForField() {
        registerUiState = registerUiState.copy(
            password = "12345678"
        )

        assertEquals(registerUiState.isPasswordError, false)
    }

    @Test
    fun registerUiState_inputPasswordNotProper_returnTrueForField() {
        registerUiState = registerUiState.copy(
            password = "1234"
        )

        assertEquals(registerUiState.isPasswordError, true)
    }

    @Test
    fun registerUiState_inputPasswordThenDelete_returnFalseForField() {
        registerUiState = registerUiState.copy(
            password = "1234567"
        )
        registerUiState = registerUiState.copy(
            password = ""
        )

        assertEquals(registerUiState.isPasswordError, false)
    }

    @Test
    fun registerUiState_inputRepeatPasswordMatch_returnFalseForField() {
        registerUiState = registerUiState.copy(
            password = "12345678",
            repeatPassword = "12345678"
        )

        assertEquals(registerUiState.isRepeatPasswordError, false)
    }

    @Test
    fun registerUiState_inputRepeatPasswordNotMatch_returnTrueForField() {
        registerUiState = registerUiState.copy(
            password = "123456789",
            repeatPassword = "1234"
        )

        assertEquals(registerUiState.isRepeatPasswordError, true)
    }

    @Test
    fun registerUiState_inputIsProper_registerEnabled() {
        registerUiState = registerUiState.copy(
            email = "indra@gmail.com",
            fullName = "indra",
            password = "12345678",
            repeatPassword = "12345678"
        )

        assertEquals(registerUiState.registerEnabled, true)
    }

    @Test
    fun registerUiState_inputIsNotProper_registerDisabled() {
        registerUiState = registerUiState.copy(
            email = "indra@gmail.com",
            fullName = "",
            password = "12345678",
            repeatPassword = "1234"
        )

        assertEquals(registerUiState.registerEnabled, false)
    }

}