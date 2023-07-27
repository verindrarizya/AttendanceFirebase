package com.verindrarizya.attendancefirebase.ui.screens.dashboard.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreen(
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .background(
                    color = Color.Cyan
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Home Screen",
                fontSize = 32.sp
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    AttendanceFirebaseTheme {
        HomeScreen()
    }
}