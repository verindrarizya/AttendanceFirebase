package com.verindrarizya.attendancefirebase.ui.screens.authentication.register

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.verindrarizya.attendancefirebase.core.data.repository.auth.AuthRepository
import com.verindrarizya.attendancefirebase.core.util.Resource
import com.verindrarizya.attendancefirebase.util.MainDispatcherRule
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit

@RunWith(AndroidJUnit4::class)
class RegisterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var mockAuthRepository: AuthRepository

    private lateinit var registerViewModel: RegisterViewModel

    @Before
    fun setup() {
        registerViewModel = RegisterViewModel(mockAuthRepository)
    }

    @Test
    fun testRegister_verifyRegisterCalled() {
        `when`(mockAuthRepository.register("indra@gmail.com", "12345678", "indra"))
            .thenReturn(flowOf(Resource.Success("Success")))

        registerViewModel.onEmailValueChange("indra@gmail.com")
        registerViewModel.onFullNameValueChange("indra")
        registerViewModel.onPasswordValueChange("12345678")
        registerViewModel.onRepeatPasswordValueChange("12345678")

        registerViewModel.register()

        verify(mockAuthRepository).register("indra@gmail.com", "12345678", "indra")
    }

    @Test
    fun testUiState_initValueIsProperWithoutErrorAndRegisterDisabled() {
        val currentUiState = registerViewModel.registerUiState.value

        assertEquals(currentUiState.isEmailError, false)
        assertEquals(currentUiState.isPasswordError, false)
        assertEquals(currentUiState.isRepeatPasswordError, false)
        assertEquals(currentUiState.registerEnabled, false)
    }

    @Test
    fun testUiState_inputEmailProper() {
        val inputtedEmail = "indra@gmail.com"
        registerViewModel.onEmailValueChange(inputtedEmail)

        val currentUiState = registerViewModel.registerUiState.value

        assertEquals(currentUiState.isEmailError, false)
    }

    @Test
    fun testUiState_inputEmailNotProper() {
        val inputtedEmail = "ansdwj"
        registerViewModel.onEmailValueChange(inputtedEmail)

        val currentUiState = registerViewModel.registerUiState.value

        assertEquals(currentUiState.isEmailError, true)
    }

    @Test
    fun testUiState_inputPasswordProper() {
        val inputtedPassword = "12345678"

        registerViewModel.onPasswordValueChange(inputtedPassword)

        val currentUiState = registerViewModel.registerUiState.value

        assertEquals(currentUiState.isPasswordError, false)
    }

    @Test
    fun testUiState_inputPasswordNotProper() {
        val inputtedPassword = "1234"

        registerViewModel.onPasswordValueChange(inputtedPassword)

        val currentUiState = registerViewModel.registerUiState.value
        assertEquals(currentUiState.isPasswordError, true)
    }

    @Test
    fun testUiState_inputRepeatPasswordProper() {
        val inputtedPassword = "12345678"
        val inputtedRepeatPassword = "12345678"

        registerViewModel.onPasswordValueChange(inputtedPassword)
        registerViewModel.onRepeatPasswordValueChange(inputtedRepeatPassword)

        val currentUiState = registerViewModel.registerUiState.value
        assertEquals(currentUiState.isRepeatPasswordError, false)
    }

    @Test
    fun testUiState_inputRepeatPasswordNotProper() {
        val inputtedPassword = "12345678"
        val inputtedRepeatPassword = "1234"

        registerViewModel.onPasswordValueChange(inputtedPassword)
        registerViewModel.onRepeatPasswordValueChange(inputtedRepeatPassword)

        val currentUiState = registerViewModel.registerUiState.value
        assertEquals(currentUiState.isRepeatPasswordError, true)
    }

    @Test
    fun testUiState_fieldInputProper_registerEnabled() {
        registerViewModel.onEmailValueChange("indra@gmail.com")
        registerViewModel.onFullNameValueChange("indra")
        registerViewModel.onPasswordValueChange("12345678")
        registerViewModel.onRepeatPasswordValueChange("12345678")

        val currentUiState = registerViewModel.registerUiState.value
        assertEquals(currentUiState.registerEnabled, true)
    }

    @Test
    fun testUiState_fieldInputNotProper_registerDisabled() {
        registerViewModel.onEmailValueChange("indra@gmail.com")
        registerViewModel.onFullNameValueChange("indra")
        registerViewModel.onPasswordValueChange("12345678")
        registerViewModel.onRepeatPasswordValueChange("1234")

        val currentUiState = registerViewModel.registerUiState.value
        assertEquals(currentUiState.registerEnabled, false)
    }
}