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
import com.verindrarizya.attendancefirebase.core.data.state.AuthState
import com.verindrarizya.attendancefirebase.ui.screens.authentication.authGraph
import com.verindrarizya.attendancefirebase.ui.screens.authentication.navigateToGlobalAuth
import com.verindrarizya.attendancefirebase.ui.screens.authentication.register.RegisterDestination
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.dashboardScreen
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.navigateToDashboard
import com.verindrarizya.attendancefirebase.ui.screens.onboarding.navigateToOnBoarding
import com.verindrarizya.attendancefirebase.ui.screens.onboarding.onBoardingScreen
import com.verindrarizya.attendancefirebase.ui.screens.preload.PreloadingDestination
import com.verindrarizya.attendancefirebase.ui.screens.preload.preloadingScreen
import kotlinx.coroutines.delay

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
                if (currentBackStack?.destination?.route != RegisterDestination::class.simpleName) {
                    navController.navigateToDashboard {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            } else {
                if (data.first) {
                    navController.navigateToGlobalAuth {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                } else {
                    delay(3_000)
                    navController.navigateToOnBoarding {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            }
        }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = PreloadingDestination
    ) {
        preloadingScreen()
        onBoardingScreen(
            onButtonStartedClicked = {
                viewModel.setUserOnBoarded()
            }
        )
        authGraph(navController = navController)
        dashboardScreen()
    }
}