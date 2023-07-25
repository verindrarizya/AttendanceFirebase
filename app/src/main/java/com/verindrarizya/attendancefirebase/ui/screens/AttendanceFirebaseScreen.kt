package com.verindrarizya.attendancefirebase.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.verindrarizya.attendancefirebase.ui.navigation.authGraph
import com.verindrarizya.attendancefirebase.ui.navigation.navigateToGlobalAuth
import com.verindrarizya.attendancefirebase.ui.navigation.popUpToInclusive
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.DashboardDestination
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.dashboardScreen
import com.verindrarizya.attendancefirebase.ui.screens.onboarding.OnBoardingDestination
import com.verindrarizya.attendancefirebase.ui.screens.onboarding.navigateToOnBoarding
import com.verindrarizya.attendancefirebase.ui.screens.onboarding.onBoardingScreen
import com.verindrarizya.attendancefirebase.ui.screens.preload.PreloadingDestination
import com.verindrarizya.attendancefirebase.ui.screens.preload.preloadingScreen

@Composable
fun AttendanceFirebaseScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: AttendanceFirebaseViewModel = viewModel()
) {
    val isUserOnBoarded: Boolean? by viewModel.isUserAlreadyOnBoarded.collectAsStateWithLifecycle()
    val currentBackStack by navController.currentBackStackEntryAsState()

    LaunchedEffect(isUserOnBoarded) {
        isUserOnBoarded?.let {
            if (it) {
                navController.navigateToGlobalAuth {
                    popUpToInclusive(
                        if (currentBackStack?.destination?.route == PreloadingDestination.routeName) {
                            PreloadingDestination
                        } else {
                            OnBoardingDestination
                        }
                    )
                }
            } else {
                navController.navigateToOnBoarding {
                    popUpToInclusive(PreloadingDestination)
                }
            }
        }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = PreloadingDestination.routeName
    ) {
        preloadingScreen()
        onBoardingScreen(
            onButtonStartedClicked = {
                viewModel.setUserOnBoarded()
            }
        )
        authGraph(navController = navController)
        dashboardScreen(
            onNavigateToAuth = {
                navController.navigateToGlobalAuth {
                    popUpToInclusive(DashboardDestination)
                }
            }
        )
    }
}