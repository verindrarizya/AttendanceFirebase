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

object RegisterDestination : Destination {
    override val routeName: String = "register"
}

fun NavController.navigateToRegister(
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(RegisterDestination.routeName, navOptions(builder))
}

fun NavGraphBuilder.registerScreen(
    onNavigateToLoginScreen: () -> Unit,
    onNavigateToDashboardScreen: () -> Unit,
    enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition,
    exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition
) {
    composable(
        route = RegisterDestination.routeName,
        enterTransition = enterTransition,
        exitTransition = exitTransition
    ) {
        RegisterScreen(
            onNavigateToLoginScreen = onNavigateToLoginScreen,
            onNavigateToDashboardScreen = onNavigateToDashboardScreen
        )
    }
}