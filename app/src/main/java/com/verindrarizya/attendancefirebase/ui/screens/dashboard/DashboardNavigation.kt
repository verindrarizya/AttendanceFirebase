package com.verindrarizya.attendancefirebase.ui.screens.dashboard

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.verindrarizya.attendancefirebase.ui.screens.Destination

object DashboardDestination : Destination {
    override val routeName: String = "dashboard"
}

fun NavController.navigateToDashboard(
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(DashboardDestination.routeName, navOptions(builder))
}

fun NavGraphBuilder.dashboardScreen(
    onNavigateToAuth: () -> Unit
) {
    composable(route = DashboardDestination.routeName) {
        DashboardScreen(
            onNavigateToAuth = onNavigateToAuth
        )
    }
}