package com.verindrarizya.attendancefirebase.ui.screens.dashboard.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.verindrarizya.attendancefirebase.ui.navigation.Destination

object ProfileDestination : Destination {
    override val routeName: String = "profile"
}

fun NavGraphBuilder.profileScreen() {
    composable(route = ProfileDestination.routeName) {
        ProfileScreen()
    }
}