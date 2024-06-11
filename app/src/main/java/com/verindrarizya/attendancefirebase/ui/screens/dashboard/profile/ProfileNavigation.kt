package com.verindrarizya.attendancefirebase.ui.screens.dashboard.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.verindrarizya.attendancefirebase.ui.navigation.Destination
import kotlinx.serialization.Serializable

@Serializable
object ProfileDestination : Destination

fun NavGraphBuilder.profileScreen() {
    composable<ProfileDestination> {
        ProfileScreen()
    }
}