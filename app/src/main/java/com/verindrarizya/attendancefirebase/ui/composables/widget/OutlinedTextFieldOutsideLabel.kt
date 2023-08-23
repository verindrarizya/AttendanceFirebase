package com.verindrarizya.attendancefirebase.ui.composables.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.verindrarizya.attendancefirebase.ui.theme.AttBlue
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import com.verindrarizya.attendancefirebase.ui.theme.TextGrayDarkish

@Composable
fun PasswordOutlinedTextFieldOutsideLabel(
    modifier: Modifier = Modifier,
    label: String,
    textFieldValue: String,
    onTextFieldValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
) {

    var isPasswordHidden by rememberSaveable { mutableStateOf(true) }

    OutlinedTextFieldOutsideLabel(
        modifier = modifier,
        label = label,
        textFieldValue = textFieldValue,
        onTextFieldValueChange = onTextFieldValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        isError = isError,
        visualTransformation = if (isPasswordHidden) PasswordVisualTransformation('*') else VisualTransformation.None,
        trailingIcon = {
            IconButton(onClick = { isPasswordHidden = !isPasswordHidden }) {
                val visibilityIcon = if (isPasswordHidden) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }

                Icon(visibilityIcon, contentDescription = "Toggle Password Visibility")
            }
        }
    )
}

@Composable
fun OutlinedTextFieldOutsideLabel(
    modifier: Modifier = Modifier,
    label: String,
    textFieldValue: String,
    onTextFieldValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            color = TextGrayDarkish,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = textFieldValue,
            onValueChange = onTextFieldValueChange,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                color = TextGrayDarkish,
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = AttBlue,
                cursorColor = AttBlue
            ),
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            isError = isError,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldOutsideLabelPreview() {
    var textFieldValue by remember { mutableStateOf("") }

    AttendanceFirebaseTheme {
        OutlinedTextFieldOutsideLabel(
            label = "Email",
            textFieldValue = textFieldValue,
            onTextFieldValueChange = { textFieldValue = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordOutlinedTextFieldOutsideLabelPreview() {
    var textFieldValue by remember { mutableStateOf("") }

    AttendanceFirebaseTheme {
        PasswordOutlinedTextFieldOutsideLabel(
            label = "Password",
            textFieldValue = textFieldValue,
            onTextFieldValueChange = { textFieldValue = it }
        )
    }
}