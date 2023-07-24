package com.verindrarizya.attendancefirebase.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.verindrarizya.attendancefirebase.ui.screens.authentication.login.LoginDestination
import com.verindrarizya.attendancefirebase.ui.screens.authentication.login.loginScreen
import com.verindrarizya.attendancefirebase.ui.screens.authentication.register.registerScreen

object GlobalAuthDestination : Destination {
    override val routeName: String = "global_auth"
}

fun NavController.navigateToGlobalAuth(
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(GlobalAuthDestination.routeName, navOptions(builder))
}

fun NavGraphBuilder.authGraph(
    onNavigateToDashboardScreen: () -> Unit,
    onNavigateToLoginScreen: () -> Unit,
    onNavigateToRegisterScreen: () -> Unit
) {
    navigation(
        startDestination = LoginDestination.routeName,
        route = GlobalAuthDestination.routeName
    ) {
        registerScreen(
            onNavigateToDashboardScreen = onNavigateToDashboardScreen,
            onNavigateToLoginScreen = onNavigateToLoginScreen
        )
        loginScreen(
            onNavigateToDashboardScreen = onNavigateToDashboardScreen,
            onNavigateToRegisterScreen = onNavigateToRegisterScreen
        )
    }
}