package com.verindrarizya.attendancefirebase.ui.screens.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.verindrarizya.attendancefirebase.ui.screens.Destination

object OnBoardingDestination : Destination {
    override val routeName: String = "OnBoardingDestination"
}

fun NavController.navigateToOnBoarding(
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(OnBoardingDestination.routeName, navOptions(builder))
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