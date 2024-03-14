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
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.verindrarizya.attendancefirebase.ui.navigation.Destination

object OnBoardingDestination : Destination {
    override val routeName: String = "OnBoardingDestination"
}

fun NavController.navigateToOnBoarding(
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(OnBoardingDestination.routeName, navOptions(builder))
}

fun NavGraphBuilder.onBoardingScreen(
    onButtonStartedClicked: () -> Unit,
    enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
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
    },
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) = {
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
) {
    composable(
        route = OnBoardingDestination.routeName,
        enterTransition = enterTransition,
        exitTransition = exitTransition
    ) {
        OnBoardingScreen(
            onButtonStartedClicked = onButtonStartedClicked,
        )
    }
}