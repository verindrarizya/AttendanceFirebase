package com.verindrarizya.attendancefirebase.ui.screens.authentication.register

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.verindrarizya.attendancefirebase.ui.navigation.Destination
import kotlinx.serialization.Serializable

@Serializable
object RegisterDestination : Destination

fun NavController.navigateToRegister(
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(RegisterDestination, navOptions(builder))
}

fun NavGraphBuilder.registerScreen(
    onNavigateToLoginScreen: () -> Unit,
    onNavigateToDashboardScreen: () -> Unit,
    enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition,
    exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition
) {
    composable<RegisterDestination>(
        enterTransition = enterTransition,
        exitTransition = exitTransition
    ) {
        RegisterScreen(
            onNavigateToLoginScreen = onNavigateToLoginScreen,
            onNavigateToDashboardScreen = onNavigateToDashboardScreen
        )
    }
}