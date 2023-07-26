package com.verindrarizya.attendancefirebase.ui.screens.authentication.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.verindrarizya.attendancefirebase.R
import com.verindrarizya.attendancefirebase.ui.composables.template.AuthTemplate
import com.verindrarizya.attendancefirebase.ui.composables.widget.OutlinedTextFieldOutsideLabel
import com.verindrarizya.attendancefirebase.ui.composables.widget.PasswordOutlinedTextFieldOutsideLabel
import com.verindrarizya.attendancefirebase.ui.composables.widget.SpanClickableText
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import com.verindrarizya.attendancefirebase.ui.theme.ButtonBgYellow
import com.verindrarizya.attendancefirebase.ui.theme.ButtonTextDarkBlueGrayish

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToRegisterScreen: () -> Unit
) {
    val loginUiState by viewModel.loginUiState.collectAsStateWithLifecycle()

    LoginScreen(
        modifier = modifier,
        onNavigateToRegisterScreen = onNavigateToRegisterScreen,
        email = loginUiState.email,
        isEmailError = loginUiState.isEmailError,
        onEmailChange = viewModel::onEmailChanged,
        password = loginUiState.password,
        isPasswordError = loginUiState.isPasswordError,
        onPasswordChange = viewModel::onPasswordChanged,
        onButtonLoginClick = viewModel::login,
        isButtonLoginEnabled = loginUiState.isLoginButtonEnabled
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNavigateToRegisterScreen: () -> Unit,
    email: String,
    isEmailError: Boolean,
    onEmailChange: (String) -> Unit,
    password: String,
    isPasswordError: Boolean,
    onPasswordChange: (String) -> Unit,
    onButtonLoginClick: () -> Unit,
    isButtonLoginEnabled: Boolean
) {
    AuthTemplate(
        modifier = modifier,
        screenTitle = stringResource(R.string.login_screen_title),
        screenDescription = stringResource(R.string.login_screen_description),
        content = {
            Spacer(Modifier.height(24.dp))
            OutlinedTextFieldOutsideLabel(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.email),
                textFieldValue = email,
                onTextFieldValueChange = onEmailChange,
                isError = isEmailError
            )
            Spacer(Modifier.height(22.dp))
            PasswordOutlinedTextFieldOutsideLabel(
                label = stringResource(R.string.password),
                textFieldValue = password,
                onTextFieldValueChange = onPasswordChange,
                isError = isPasswordError
            )
            Spacer(Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onButtonLoginClick()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonBgYellow,
                    contentColor = ButtonTextDarkBlueGrayish
                ),
                enabled = isButtonLoginEnabled
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                )
            }
            Spacer(Modifier.height(12.dp))
            SpanClickableText(
                modifier = Modifier.fillMaxWidth(),
                onSpanTextClick = { onNavigateToRegisterScreen() },
                regularText = "Don't have an account? Please",
                clickableText = "Register",
                fontSize = 12.sp
            )
            Spacer(Modifier.height(21.dp))
        }
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    AttendanceFirebaseTheme {
        LoginScreen(
            onNavigateToRegisterScreen = { },
            email = "",
            isEmailError = false,
            onEmailChange = {},
            password = "",
            isPasswordError = false,
            onPasswordChange = {},
            onButtonLoginClick = {},
            isButtonLoginEnabled = true
        )
    }
}