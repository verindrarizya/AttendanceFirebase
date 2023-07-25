package com.verindrarizya.attendancefirebase.ui.composables.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.verindrarizya.attendancefirebase.R
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import com.verindrarizya.attendancefirebase.ui.theme.Whiteish

@Composable
fun AuthTemplate(
    modifier: Modifier = Modifier,
    screenTitle: String,
    screenDescription: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .imePadding()
            .paint(
                painter = painterResource(R.drawable.bg_auth),
                contentScale = ContentScale.FillBounds
            )
            .padding(horizontal = 8.dp)
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(20.dp))
        Column(
            modifier = Modifier.padding(
                horizontal = 20.dp,
                vertical = 30.dp
            )
        ) {
            Text(
                text = screenTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Whiteish
            )
            Spacer(Modifier.height(18.dp))
            Text(
                text = screenDescription,
                fontSize = 15.sp,
                color = Whiteish
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(
                    color = Whiteish,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp
                    )
                )
                .padding(
                    top = 16.dp,
                    start = 32.dp,
                    end = 32.dp
                )
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthTemplatePreview() {
    AttendanceFirebaseTheme {
        AuthTemplate(
            screenTitle = stringResource(R.string.login_screen_title),
            screenDescription = stringResource(R.string.login_screen_description)
        ) {
            Text(text = "TEST CONTENT", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text(
                modifier = Modifier.weight(1f),
                text = "TEST CONTENT",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "TEST CONTENT", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text(text = "TEST CONTENT", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        }
    }
}