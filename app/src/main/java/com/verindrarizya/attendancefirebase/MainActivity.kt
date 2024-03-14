package com.verindrarizya.attendancefirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.verindrarizya.attendancefirebase.ui.screens.AttendanceFirebaseScreen
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            AttendanceFirebaseTheme {
                AttendanceFirebaseScreen()
            }
        }
    }
}