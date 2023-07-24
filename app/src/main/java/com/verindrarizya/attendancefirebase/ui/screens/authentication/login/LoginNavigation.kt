package com.verindrarizya.attendancefirebase.ui.screens.authentication.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.verindrarizya.attendancefirebase.ui.navigation.Destination

object LoginDestination : Destination {
    override val routeName: String = "login"
}

fun NavController.navigateToLogin(
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(LoginDestination.routeName, navOptions(builder))
}

fun NavGraphBuilder.loginScreen(
    onNavigateToDashboardScreen: () -> Unit,
    onNavigateToRegisterScreen: () -> Unit
) {
    composable(route = LoginDestination.routeName) {
        LoginScreen(
            onNavigateToDashboardScreen = onNavigateToDashboardScreen,
            onNavigateToRegisterScreen = onNavigateToRegisterScreen
        )
    }
}