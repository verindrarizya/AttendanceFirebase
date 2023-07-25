package com.verindrarizya.attendancefirebase.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.verindrarizya.attendancefirebase.ui.screens.authentication.login.LoginDestination
import com.verindrarizya.attendancefirebase.ui.screens.authentication.login.loginScreen
import com.verindrarizya.attendancefirebase.ui.screens.authentication.login.navigateToLogin
import com.verindrarizya.attendancefirebase.ui.screens.authentication.register.RegisterDestination
import com.verindrarizya.attendancefirebase.ui.screens.authentication.register.navigateToRegister
import com.verindrarizya.attendancefirebase.ui.screens.authentication.register.registerScreen
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.navigateToDashboard

object GlobalAuthDestination : Destination {
    override val routeName: String = "global_auth"
}

fun NavController.navigateToGlobalAuth(
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(GlobalAuthDestination.routeName, navOptions(builder))
}

fun NavGraphBuilder.authGraph(
    navController: NavController
) {
    navigation(
        startDestination = LoginDestination.routeName,
        route = GlobalAuthDestination.routeName
    ) {
        registerScreen(
            onNavigateToDashboardScreen = {
                navController.navigateToDashboard {
                    popUpToInclusive(RegisterDestination)
                }
            },
            onNavigateToLoginScreen = {
                navController.navigateToLogin {
                    popUpToInclusive(RegisterDestination)
                }
            }
        )
        loginScreen(
            onNavigateToDashboardScreen = {
                navController.navigateToDashboard {
                    popUpToInclusive(LoginDestination)
                }
            },
            onNavigateToRegisterScreen = {
                navController.navigateToRegister {
                    popUpToInclusive(LoginDestination)
                }
            }
        )
    }
}