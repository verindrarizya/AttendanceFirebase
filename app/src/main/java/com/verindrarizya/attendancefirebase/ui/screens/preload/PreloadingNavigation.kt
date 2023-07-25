package com.verindrarizya.attendancefirebase.ui.screens.preload

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.verindrarizya.attendancefirebase.ui.navigation.Destination

object PreloadingDestination : Destination {
    override val routeName: String = "PreloadingDestination"
}

fun NavGraphBuilder.preloadingScreen() {
    composable(PreloadingDestination.routeName) {
        PreloadingScreen()
    }
}