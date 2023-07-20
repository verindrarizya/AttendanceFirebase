package com.verindrarizya.attendancefirebase.ui.screens.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.verindrarizya.attendancefirebase.R

data class OnBoardingPagerItemContent(
    @DrawableRes val drawableRes: Int,
    @StringRes val descriptionRes: Int
)

val onBoardingPagerItemContentContents: List<OnBoardingPagerItemContent> = listOf(
    OnBoardingPagerItemContent(
        R.drawable.ic_time_attendance,
        R.string.onboarding_record_attendance_message
    ),
    OnBoardingPagerItemContent(
        R.drawable.ic_attendance_history,
        R.string.onboarding_attendance_history_message
    ),
    OnBoardingPagerItemContent(
        R.drawable.ic_office,
        R.string.onboarding_available_office_message
    )
)