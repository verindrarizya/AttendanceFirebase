package com.verindrarizya.attendancefirebase.ui.screens.dashboard.history

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.verindrarizya.attendancefirebase.ui.navigation.Destination
import kotlinx.serialization.Serializable

@Serializable
object HistoryDestination : Destination

fun NavGraphBuilder.historyScreen() {
    composable<HistoryDestination> {
        HistoryScreen()
    }
}