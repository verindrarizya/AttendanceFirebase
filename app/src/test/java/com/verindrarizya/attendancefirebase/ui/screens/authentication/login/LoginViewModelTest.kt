package com.verindrarizya.attendancefirebase.ui.screens.authentication.login

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
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var mockAuthRepository: AuthRepository

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setup() {
        loginViewModel = LoginViewModel(mockAuthRepository)
    }

    @Test
    fun testLogin_assertLoginRepositoryMethodCalled() {
        `when`(mockAuthRepository.login("indra@gmail.com", "12345678"))
            .thenReturn(flowOf(Resource.Success("Success")))

        loginViewModel.onEmailChanged("indra@gmail.com")
        loginViewModel.onPasswordChanged("12345678")
        loginViewModel.login()

        verify(mockAuthRepository).login("indra@gmail.com", "12345678")
    }

    @Test
    fun testUiState_initValueIsProper() {
        val currentLoginUiState = loginViewModel.loginUiState.value

        assertEquals(currentLoginUiState.email, "")
        assertEquals(currentLoginUiState.password, "")
        assertEquals(currentLoginUiState.isEmailError, false)
        assertEquals(currentLoginUiState.isPasswordError, false)
        assertEquals(currentLoginUiState.isLoginButtonEnabled, false)
    }

    @Test
    fun testUiState_inputNotMatchedEmail() {
        loginViewModel.onEmailChanged("not proper")

        val currentUiState = loginViewModel.loginUiState.value

        assertEquals(currentUiState.email, "not proper")
        assertEquals(currentUiState.isEmailError, true)
    }

    @Test
    fun testUiState_inputEmailMatched() {
        loginViewModel.onEmailChanged("indra@gmail.com")

        val currentUiState = loginViewModel.loginUiState.value

        assertEquals(currentUiState.email, "indra@gmail.com")
        assertEquals(currentUiState.isEmailError, false)
    }

    @Test
    fun testUiState_inputPasswordLessThan8Char() {
        loginViewModel.onPasswordChanged("123")

        val currentUiState = loginViewModel.loginUiState.value

        assertEquals(currentUiState.password, "123")
        assertEquals(currentUiState.isPasswordError, true)
    }

    @Test
    fun testUiState_inputConditionNotFulfilledEmailPassword_uiStateButtonDisabledAndFieldsError() {
            val inputEmailNotProper = "not proper"
            val inputPasswordNotProper = "1234"
            loginViewModel.onEmailChanged(inputEmailNotProper)
            loginViewModel.onPasswordChanged(inputPasswordNotProper)

            val currentUiState = loginViewModel.loginUiState.value

            assertEquals(currentUiState.email, inputEmailNotProper)
            assertEquals(currentUiState.password, inputPasswordNotProper)
            assertEquals(currentUiState.isEmailError, true)
            assertEquals(currentUiState.isPasswordError, true)
            assertEquals(currentUiState.isLoginButtonEnabled, false)
        }

    @Test
    fun testUiState_inputConditionEmailPasswordFulfilled_uiStateButtonEnabledFieldsNotError() {
            val inputEmail = "indra@gmail.com"
            val inputPassword = "12345678"
            loginViewModel.onEmailChanged(inputEmail)
            loginViewModel.onPasswordChanged(inputPassword)

            val currentUiState = loginViewModel.loginUiState.value

            assertEquals(currentUiState.email, inputEmail)
            assertEquals(currentUiState.password, inputPassword)
            assertEquals(currentUiState.isEmailError, false)
            assertEquals(currentUiState.isPasswordError, false)
            assertEquals(currentUiState.isLoginButtonEnabled, true)
        }
}