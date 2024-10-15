package com.verindrarizya.attendancefirebase.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.verindrarizya.attendancefirebase.core.data.state.AuthState
import com.verindrarizya.attendancefirebase.ui.screens.authentication.GlobalAuthDestination
import com.verindrarizya.attendancefirebase.ui.screens.authentication.authGraph
import com.verindrarizya.attendancefirebase.ui.screens.authentication.register.RegisterDestination
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.DashboardDestination
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.DashboardScreen
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.DashboardScreenNavigation
import com.verindrarizya.attendancefirebase.ui.screens.onboarding.OnBoardingDestination
import com.verindrarizya.attendancefirebase.ui.screens.onboarding.OnBoardingScreen
import com.verindrarizya.attendancefirebase.ui.screens.onboarding.OnBoardingScreenNavigation
import com.verindrarizya.attendancefirebase.ui.screens.preload.PreloadingDestination
import com.verindrarizya.attendancefirebase.ui.screens.preload.PreloadingScreen
import com.verindrarizya.attendancefirebase.ui.screens.preload.PreloadingScreenNavigation
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
                    navController.navigate(DashboardDestination) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            } else {
                if (data.first) {
                    navController.navigate(GlobalAuthDestination) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                } else {
                    delay(3_000)
                    navController.navigate(OnBoardingDestination) {
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
        composable<PreloadingDestination>(
            enterTransition = PreloadingScreenNavigation.enterTransition,
            exitTransition = PreloadingScreenNavigation.exitTransition
        ) {
            PreloadingScreen()
        }
        composable<OnBoardingDestination>(
            enterTransition = OnBoardingScreenNavigation.enterTransition,
            exitTransition = OnBoardingScreenNavigation.exitTransition
        ) {
            OnBoardingScreen(onButtonStartedClicked = {
                viewModel.setUserOnBoarded()
            })
        }
        authGraph(navController = navController)
        composable<DashboardDestination>(
            enterTransition = DashboardScreenNavigation.enterTransition,
            exitTransition = DashboardScreenNavigation.exitTransition
        ) {
            DashboardScreen()
        }
    }
}