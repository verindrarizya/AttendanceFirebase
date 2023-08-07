package com.verindrarizya.attendancefirebase.ui.composables.widget

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import com.verindrarizya.attendancefirebase.ui.theme.BgMustard
import com.verindrarizya.attendancefirebase.ui.theme.ButtonBgGreen

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    backgroundColor: Color,
    buttonSize: Dp = 250.dp,
    fontSize: TextUnit = 35.sp
) {
    Button(
        modifier = modifier
            .size(buttonSize),
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        )
    ) {
        Text(
            text = text,
            color = Color.White,
            style = LocalTextStyle.current.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = fontSize
            )
        )
    }
}

@Preview
@Composable
fun CircleButtonCheckInPreview() {
    AttendanceFirebaseTheme {
        CircleButton(
            onClick = {},
            text = "Check In",
            backgroundColor = ButtonBgGreen
        )
    }
}

@Preview
@Composable
fun CircleButtonCheckOutPreview() {
    AttendanceFirebaseTheme {
        CircleButton(
            onClick = { },
            text = "Check Out",
            backgroundColor = BgMustard
        )
    }
}