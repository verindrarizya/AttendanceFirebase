package com.verindrarizya.attendancefirebase.ui.screens.dashboard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.verindrarizya.attendancefirebase.R
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.home.HomeDestination
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.profile.ProfileDestination

val dashboardItems = listOf(
    DashboardItemScreen.HomeScreen,
    DashboardItemScreen.ProfileScreen,
)

sealed class DashboardItemScreen(
    val routeName: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
) {
    object HomeScreen : DashboardItemScreen(
        routeName = HomeDestination.routeName,
        title = R.string.home,
        icon = R.drawable.ic_home
    )

    object ProfileScreen : DashboardItemScreen(
        routeName = ProfileDestination.routeName,
        title = R.string.profile,
        icon = R.drawable.ic_profile_nav
    )
}