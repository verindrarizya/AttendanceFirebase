package com.verindrarizya.attendancefirebase.ui.screens.authentication.login

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.verindrarizya.attendancefirebase.R
import com.verindrarizya.attendancefirebase.core.util.Resource
import com.verindrarizya.attendancefirebase.ui.composables.template.AuthTemplate
import com.verindrarizya.attendancefirebase.ui.composables.widget.OutlinedTextFieldOutsideLabel
import com.verindrarizya.attendancefirebase.ui.composables.widget.PasswordOutlinedTextFieldOutsideLabel
import com.verindrarizya.attendancefirebase.ui.composables.widget.SpanClickableText
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import com.verindrarizya.attendancefirebase.ui.theme.ButtonBgYellow
import com.verindrarizya.attendancefirebase.ui.theme.ButtonTextDarkBlueGrayish

@SuppressLint("ResourceAsColor")
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToRegisterScreen: () -> Unit
) {
    val loginUiState by viewModel.loginUiState.collectAsStateWithLifecycle()
    val loginResourceState by viewModel.loginResourceState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.message.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LoginScreen(
        modifier = modifier,
        loginResource = loginResourceState,
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
    loginResource: Resource<String>,
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
                isError = isEmailError,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
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
                    if (loginResource !is Resource.Loading) {
                        onButtonLoginClick()
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ButtonBgYellow,
                    contentColor = ButtonTextDarkBlueGrayish
                ),
                enabled = isButtonLoginEnabled,
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 14.dp
                )
            ) {
                if (loginResource is Resource.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(14.dp)
                    )
                } else {
                    Text(text = stringResource(R.string.login))
                }
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
            isButtonLoginEnabled = true,
            loginResource = Resource.Init
        )
    }
}