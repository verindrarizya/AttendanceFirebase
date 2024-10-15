package com.verindrarizya.attendancefirebase.ui.screens.onboarding

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import com.verindrarizya.attendancefirebase.ui.navigation.Destination
import kotlinx.serialization.Serializable

@Serializable
object OnBoardingDestination : Destination

object OnBoardingScreenNavigation {
    val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        val slideTop = slideInVertically(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            ),
            initialOffsetY = { 100 }
        )

        val fadeIn = fadeIn(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            ),
            initialAlpha = 0.8f
        )

        slideTop + fadeIn
    }
    val exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) = {
        val slideToLeft = slideOutHorizontally(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            ),
            targetOffsetX = { -300 }
        )

        val fadeOut = fadeOut(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            )
        )

        slideToLeft + fadeOut
    }
}