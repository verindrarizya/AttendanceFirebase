package com.verindrarizya.attendancefirebase.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
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
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = OnBoardingDestination.routeName
    ) {
        onBoardingScreen(
            onNavigateToLoginScreen = {
                navController.navigateToLogin {
                    popUpToInclusive(OnBoardingDestination)
                }
            },
            onNavigateToRegisterScreen = {
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