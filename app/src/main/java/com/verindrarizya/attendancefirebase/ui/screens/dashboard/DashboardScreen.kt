package com.verindrarizya.attendancefirebase.ui.screens.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.history.historyScreen
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.home.homeScreen
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.profile.profileScreen
import com.verindrarizya.attendancefirebase.ui.theme.AttBlue
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import com.verindrarizya.attendancefirebase.ui.theme.MontserratFamily
import com.verindrarizya.attendancefirebase.ui.theme.Whiteish

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            DashboardBottomNav(
                currentDestination = currentDestination,
                onBottomNavItemClick = { screen ->
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            modifier = modifier
                .padding(paddingValues),
            navController = navController,
            startDestination = DashboardItemScreen.HomeScreen.route,
        ) {
            homeScreen()
            historyScreen()
            profileScreen()
        }
    }
}

@Composable
fun DashboardBottomNav(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    bottomNavItems: List<DashboardItemScreen> = dashboardItems,
    onBottomNavItemClick: (DashboardItemScreen) -> Unit,
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Whiteish
    ) {
        bottomNavItems.forEach {
            val isSelected = currentDestination?.hierarchy?.any { navDestination ->
                navDestination == currentDestination
            } == true

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(it.icon),
                        contentDescription = null,
                        tint = if (isSelected)
                            Color.Unspecified
                        else
                            Color.Gray
                    )
                },
                label = {
                    Text(
                        text = stringResource(it.title),
                        color = if (isSelected)
                            AttBlue
                        else
                            Color.Gray,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = MontserratFamily
                    )
                },
                selected = isSelected,
                onClick = { onBottomNavItemClick(it) },
            )
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    AttendanceFirebaseTheme {
        DashboardScreen()
    }
}