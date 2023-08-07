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
import com.verindrarizya.attendancefirebase.ui.screens.authentication.register.RegisterDestination
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.dashboardScreen
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.navigateToDashboard
import com.verindrarizya.attendancefirebase.ui.screens.onboarding.navigateToOnBoarding
import com.verindrarizya.attendancefirebase.ui.screens.onboarding.onBoardingScreen
import com.verindrarizya.attendancefirebase.ui.screens.preload.PreloadingDestination
import com.verindrarizya.attendancefirebase.ui.screens.preload.preloadingScreen
import com.verindrarizya.attendancefirebase.util.AuthState

@Composable
fun AttendanceFirebaseScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: AttendanceFirebaseViewModel = viewModel()
) {
    val authenticationState by viewModel.authenticationState.collectAsStateWithLifecycle()
    val currentBackStack by navController.currentBackStackEntryAsState()

    LaunchedEffect(authenticationState) {
        authenticationState?.let { data ->
            // first -> onboarded flag,
            // second -> AuthState
            if (data.second == AuthState.SignedIn) {
                if (currentBackStack?.destination?.route != RegisterDestination.routeName) {
                    navController.navigateToDashboard {
                        popUpToInclusive(navController.graph.id)
                    }
                }
            } else {
                if (data.first) {
                    navController.navigateToGlobalAuth {
                        popUpToInclusive(navController.graph.id)
                    }
                } else {
                    navController.navigateToOnBoarding {
                        popUpToInclusive(navController.graph.id)
                    }
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
        )
    }
}