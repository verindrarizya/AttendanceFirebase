package com.verindrarizya.attendancefirebase.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.verindrarizya.attendancefirebase.ui.navigation.GlobalAuthDestination
import com.verindrarizya.attendancefirebase.ui.navigation.authGraph
import com.verindrarizya.attendancefirebase.ui.navigation.navigateToGlobalAuth
import com.verindrarizya.attendancefirebase.ui.navigation.popUpToInclusive
import com.verindrarizya.attendancefirebase.ui.screens.authentication.login.LoginDestination
import com.verindrarizya.attendancefirebase.ui.screens.authentication.login.navigateToLogin
import com.verindrarizya.attendancefirebase.ui.screens.authentication.register.RegisterDestination
import com.verindrarizya.attendancefirebase.ui.screens.authentication.register.navigateToRegister
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.DashboardDestination
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.dashboardScreen
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.navigateToDashboard
import com.verindrarizya.attendancefirebase.ui.screens.onboarding.OnBoardingDestination
import com.verindrarizya.attendancefirebase.ui.screens.onboarding.onBoardingScreen

@Composable
fun AttendanceFirebaseScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: AttendanceFirebaseViewModel = viewModel()
) {
    val isUserOnBoarded by viewModel.isUserAlreadyOnBoarded.collectAsStateWithLifecycle()

    isUserOnBoarded?.let { flag ->
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = if (flag) {
                GlobalAuthDestination.routeName
            } else {
                OnBoardingDestination.routeName
            }
        ) {
            onBoardingScreen(
                onNavigateToLoginScreen = {
                    viewModel.setUserOnBoarded()
                    navController.navigateToLogin {
                        popUpToInclusive(OnBoardingDestination)
                    }
                },
                onNavigateToRegisterScreen = {
                    viewModel.setUserOnBoarded()
                    navController.navigateToRegister {
                        popUpToInclusive(OnBoardingDestination)
                    }
                }
            )
            authGraph(
                onNavigateToDashboardScreen = {
                    navController.navigateToDashboard {
                        popUpToInclusive(
                            if (navController.currentDestination?.route == RegisterDestination.routeName) {
                                RegisterDestination
                            } else {
                                LoginDestination
                            }
                        )
                    }
                },
                onNavigateToLoginScreen = {
                    navController.navigateToLogin {
                        popUpToInclusive(
                            if (navController.currentDestination?.route == RegisterDestination.routeName) {
                                RegisterDestination
                            } else {
                                LoginDestination
                            }
                        )
                    }
                },
                onNavigateToRegisterScreen = {
                    navController.navigateToRegister {
                        popUpToInclusive(
                            if (navController.currentDestination?.route == RegisterDestination.routeName) {
                                RegisterDestination
                            } else {
                                LoginDestination
                            }
                        )
                    }
                }
            )
            dashboardScreen(
                onNavigateToAuth = {
                    navController.navigateToGlobalAuth {
                        popUpToInclusive(DashboardDestination)
                    }
                }
            )
        }
    }
}