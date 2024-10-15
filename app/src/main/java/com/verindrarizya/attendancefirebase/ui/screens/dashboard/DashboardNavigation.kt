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
import com.verindrarizya.attendancefirebase.ui.navigation.Destination
import kotlinx.serialization.Serializable

@Serializable
object DashboardDestination : Destination

object DashboardScreenNavigation {
    val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
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
    }
    val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
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
}