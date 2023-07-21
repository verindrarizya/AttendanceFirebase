package com.verindrarizya.attendancefirebase.ui.screens.authentication.register

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.verindrarizya.attendancefirebase.R
import com.verindrarizya.attendancefirebase.ui.composables.template.AuthTemplate
import com.verindrarizya.attendancefirebase.ui.composables.widget.OutlinedTextFieldOutsideLabel
import com.verindrarizya.attendancefirebase.ui.composables.widget.SpanClickableText
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import com.verindrarizya.attendancefirebase.ui.theme.ButtonBgYellow
import com.verindrarizya.attendancefirebase.ui.theme.ButtonTextDarkBlueGrayish

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navigateToLoginScreen: () -> Unit,
    navigateToDashboardScreen: () -> Unit
) {
    AuthTemplate(
        modifier = modifier,
        screenTitle = stringResource(R.string.register_screen_title),
        screenDescription = stringResource(R.string.register_screen_description),
        content = {
            Spacer(Modifier.height(24.dp))
            OutlinedTextFieldOutsideLabel(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.email),
                textFieldValue = "",
                onTextFieldValueChange = {}
            )
            Spacer(Modifier.height(22.dp))
            OutlinedTextFieldOutsideLabel(
                label = stringResource(R.string.full_name),
                textFieldValue = "",
                onTextFieldValueChange = {}
            )
            Spacer(Modifier.height(22.dp))
            OutlinedTextFieldOutsideLabel(
                label = stringResource(R.string.password),
                textFieldValue = "",
                onTextFieldValueChange = {}
            )
            Spacer(Modifier.height(22.dp))
            OutlinedTextFieldOutsideLabel(
                label = stringResource(R.string.repeat_password),
                textFieldValue = "",
                onTextFieldValueChange = {}
            )
            Spacer(Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navigateToDashboardScreen() },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonBgYellow,
                    contentColor = ButtonTextDarkBlueGrayish
                )
            ) {
                Text(
                    text = stringResource(R.string.register),
                )
            }
            Spacer(Modifier.height(12.dp))
            SpanClickableText(
                modifier = Modifier.fillMaxWidth(),
                onSpanTextClick = { navigateToLoginScreen() },
                regularText = "Already have an account? Please",
                clickableText = "Login",
                fontSize = 12.sp
            )
            Spacer(Modifier.height(21.dp))
        }
    )
}

@Preview
@Composable
fun RegisterScreenPreview() {
    AttendanceFirebaseTheme {
        RegisterScreen(
            navigateToLoginScreen = {},
            navigateToDashboardScreen = {}
        )
    }
}