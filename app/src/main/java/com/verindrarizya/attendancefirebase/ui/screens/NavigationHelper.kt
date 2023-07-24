package com.verindrarizya.attendancefirebase.ui.screens

import androidx.navigation.NavOptionsBuilder

fun NavOptionsBuilder.popUpToInclusive(destination: Destination) {
    popUpTo(destination.routeName) {
        inclusive = true
    }
}