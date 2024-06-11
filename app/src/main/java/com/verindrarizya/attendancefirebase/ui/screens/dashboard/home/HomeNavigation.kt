package com.verindrarizya.attendancefirebase.ui.screens.dashboard.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.verindrarizya.attendancefirebase.ui.navigation.Destination
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination : Destination

fun NavGraphBuilder.homeScreen() {
    composable<HomeDestination> {
        HomeScreen()
    }
}