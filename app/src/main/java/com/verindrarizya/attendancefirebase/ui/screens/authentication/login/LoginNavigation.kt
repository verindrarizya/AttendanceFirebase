package com.verindrarizya.attendancefirebase.ui.screens.authentication.login

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
object LoginDestination : Destination

fun NavController.navigateToLogin(
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(LoginDestination, navOptions(builder))
}

fun NavGraphBuilder.loginScreen(
    onNavigateToRegisterScreen: () -> Unit,
    enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition,
    exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition
) {
    composable<LoginDestination>(
        enterTransition = enterTransition,
        exitTransition = exitTransition
    ) {
        LoginScreen(
            onNavigateToRegisterScreen = onNavigateToRegisterScreen
        )
    }
}