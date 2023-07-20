package com.verindrarizya.attendancefirebase.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.verindrarizya.attendancefirebase.ui.screens.onboarding.OnBoardingDestination
import com.verindrarizya.attendancefirebase.ui.screens.onboarding.OnBoardingScreen

@Composable
fun AttendanceFirebaseScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = OnBoardingDestination.routeName
    ) {
        composable(OnBoardingDestination.routeName) {
            OnBoardingScreen(
                onButtonSignUpClicked = {},
                onButtonLoginClicked = {}
            )
        }
    }
}