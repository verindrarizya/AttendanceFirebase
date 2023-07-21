package com.verindrarizya.attendancefirebase.ui.composables.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verindrarizya.attendancefirebase.ui.theme.AttBlue
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import com.verindrarizya.attendancefirebase.ui.theme.TextGrayDarkish

@Composable
fun OutlinedTextFieldOutsideLabel(
    modifier: Modifier = Modifier,
    label: String,
    textFieldValue: String,
    onTextFieldValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            color = TextGrayDarkish,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = onTextFieldValueChange,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                color = TextGrayDarkish,
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AttBlue,
                cursorColor = AttBlue
            )
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