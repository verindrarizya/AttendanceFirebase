package com.verindrarizya.attendancefirebase.ui.screens.dashboard.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    ProfileScreen(
        modifier = modifier,
        onButtonSignOutClick = viewModel::signOut
    )
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onButtonSignOutClick: () -> Unit
) {
    Scaffold { paddingValues ->
        Box(
            modifier = modifier
                .background(
                    color = Color.Magenta
                )
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = onButtonSignOutClick) {
                Text(text = "Sign Out", fontSize = 32.sp)
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    AttendanceFirebaseTheme {
        ProfileScreen(
            onButtonSignOutClick = {}
        )
    }
}