package com.verindrarizya.attendancefirebase.ui.screens.authentication

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.verindrarizya.attendancefirebase.ui.navigation.Destination
import com.verindrarizya.attendancefirebase.ui.screens.authentication.login.LoginDestination
import com.verindrarizya.attendancefirebase.ui.screens.authentication.login.loginScreen
import com.verindrarizya.attendancefirebase.ui.screens.authentication.login.navigateToLogin
import com.verindrarizya.attendancefirebase.ui.screens.authentication.register.RegisterDestination
import com.verindrarizya.attendancefirebase.ui.screens.authentication.register.navigateToRegister
import com.verindrarizya.attendancefirebase.ui.screens.authentication.register.registerScreen
import com.verindrarizya.attendancefirebase.ui.screens.dashboard.navigateToDashboard
import kotlinx.serialization.Serializable

@Serializable
object GlobalAuthDestination : Destination

private object GlobalAuthNavigationAnimation {
    val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        val slideUp = slideInVertically(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            ),
            initialOffsetY = { 300 }
        )

        val fadeOut = fadeIn(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            ),
            initialAlpha = 0.8f
        )

        slideUp + fadeOut
    }
    val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        val slideDown = slideOutVertically(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            ),
            targetOffsetY = { 300 }
        )

        val fadeOut = fadeOut(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            ),
            targetAlpha = 0.8f
        )

        slideDown + fadeOut
    }
}

fun NavController.navigateToGlobalAuth(
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(GlobalAuthDestination, navOptions(builder))
}

fun NavGraphBuilder.authGraph(
    navController: NavController
) {
    navigation<GlobalAuthDestination>(
        startDestination = LoginDestination
    ) {
        registerScreen(
            onNavigateToLoginScreen = {
                navController.navigateToLogin {
                    popUpTo(RegisterDestination) { inclusive = true }
                }
            },
            onNavigateToDashboardScreen = {
                navController.navigateToDashboard {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            },
            enterTransition = GlobalAuthNavigationAnimation.enterTransition,
            exitTransition = GlobalAuthNavigationAnimation.exitTransition
        )
        loginScreen(
            onNavigateToRegisterScreen = {
                navController.navigateToRegister {
                    popUpTo(LoginDestination) { inclusive = true }
                }
            },
            enterTransition = GlobalAuthNavigationAnimation.enterTransition,
            exitTransition = GlobalAuthNavigationAnimation.exitTransition
        )
    }
}