package com.verindrarizya.attendancefirebase.ui.screens.dashboard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.verindrarizya.attendancefirebase.R
import com.verindrarizya.attendancefirebase.ui.navigation.Destination
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.history.HistoryDestination
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.home.HomeDestination
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.profile.ProfileDestination

val dashboardItems = listOf(
    DashboardItemScreen.HomeScreen,
    DashboardItemScreen.HistoryScreen,
    DashboardItemScreen.ProfileScreen
)

sealed class DashboardItemScreen(
    val route: Destination,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
) {
    data object HomeScreen : DashboardItemScreen(
        route = HomeDestination,
        title = R.string.home,
        icon = R.drawable.ic_home
    )

    data object ProfileScreen : DashboardItemScreen(
        route = ProfileDestination,
        title = R.string.profile,
        icon = R.drawable.ic_profile_nav
    )

    data object HistoryScreen : DashboardItemScreen(
        route = HistoryDestination,
        title = R.string.history,
        icon = R.drawable.ic_history
    )
}