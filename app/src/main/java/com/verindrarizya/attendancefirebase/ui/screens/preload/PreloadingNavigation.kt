package com.verindrarizya.attendancefirebase.ui.screens.preload

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.verindrarizya.attendancefirebase.ui.navigation.Destination

object PreloadingDestination : Destination {
    override val routeName: String = "PreloadingDestination"
}

fun NavGraphBuilder.preloadingScreen(
    enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        val scaleIn = scaleIn(
            animationSpec = tween(
                durationMillis = 300,
            ),
            initialScale = 0.8f,
        )

        val fadeIn = fadeIn(
            animationSpec = tween(
                300
            )
        )

        scaleIn + fadeIn
    },
    exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        val scaleOutContainer = scaleOut(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            ),
            targetScale = 1.2f
        )

        val fadeOut = fadeOut(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            )
        )

        scaleOutContainer + fadeOut
    }
) {
    composable(
        route = PreloadingDestination.routeName,
        enterTransition = enterTransition,
        exitTransition = exitTransition
    ) {
        PreloadingScreen()
    }
}