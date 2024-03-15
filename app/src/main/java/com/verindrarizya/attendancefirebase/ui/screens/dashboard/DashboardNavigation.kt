package com.verindrarizya.attendancefirebase.ui.screens.dashboard

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.verindrarizya.attendancefirebase.ui.navigation.Destination

object DashboardDestination : Destination {
    override val routeName: String = "dashboard"
}

fun NavController.navigateToDashboard(
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(DashboardDestination.routeName, navOptions(builder))
}

fun NavGraphBuilder.dashboardScreen(
    enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        val slideInLeft = slideInHorizontally(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            ),
            initialOffsetX = { 200 }
        )

        val fadeIn = fadeIn(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            ),
            initialAlpha = 0.8f
        )

        slideInLeft + fadeIn
    },
    exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        val slideOutRight = slideOutHorizontally(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            ),
            targetOffsetX = { 200 }
        )

        val fadeOut = fadeOut(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            ),
            targetAlpha = 0.8f
        )

        slideOutRight + fadeOut
    }
) {
    composable(
        route = DashboardDestination.routeName,
        enterTransition = enterTransition,
        exitTransition = exitTransition
    ) {
        DashboardScreen()
    }
}