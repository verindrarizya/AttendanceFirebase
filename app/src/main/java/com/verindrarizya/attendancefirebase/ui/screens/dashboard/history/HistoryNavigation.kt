package com.verindrarizya.attendancefirebase.ui.screens.dashboard.history

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.verindrarizya.attendancefirebase.ui.navigation.Destination

object HistoryDestination : Destination {
    override val routeName: String = "history"
}

fun NavGraphBuilder.historyScreen() {
    composable(route = HistoryDestination.routeName) {
        HistoryScreen()
    }
}