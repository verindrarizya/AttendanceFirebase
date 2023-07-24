package com.verindrarizya.attendancefirebase.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme


@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onNavigateToAuth: () -> Unit
) {
    Column(
        modifier = modifier
            .background(
                color = Color.LightGray
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Dashboard Screen")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { onNavigateToAuth() }) {
            Text("Sign Out")
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    AttendanceFirebaseTheme {
        DashboardScreen(
            onNavigateToAuth = {}
        )
    }
}