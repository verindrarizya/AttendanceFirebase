package com.verindrarizya.attendancefirebase.ui.screens.onboarding

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.verindrarizya.attendancefirebase.ui.navigation.Destination

object OnBoardingDestination : Destination {
    override val routeName: String = "OnBoardingDestination"
}

fun NavGraphBuilder.onBoardingScreen(
    onNavigateToRegisterScreen: () -> Unit,
    onNavigateToLoginScreen: () -> Unit
) {
    composable(route = OnBoardingDestination.routeName) {
        OnBoardingScreen(
            onNavigateToLoginScreen = {
                onNavigateToLoginScreen()
            },
            onNavigateToRegisterScreen = {
                onNavigateToRegisterScreen()
            }
        )
    }
}