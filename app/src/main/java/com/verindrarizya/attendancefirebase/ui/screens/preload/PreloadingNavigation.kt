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
import com.verindrarizya.attendancefirebase.ui.navigation.Destination
import kotlinx.serialization.Serializable

@Serializable
object PreloadingDestination : Destination

object PreloadingScreenNavigation {
    val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
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
    }

    val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
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
}