package com.verindrarizya.attendancefirebase.data.model.firebase

import com.google.firebase.database.IgnoreExtraProperties
import com.verindrarizya.attendancefirebase.ui.model.UserProfile

@IgnoreExtraProperties
data class UserProfileSnapshot(
    val address: String? = null,
    val employeeNumber: String? = null,
    val jobTitle: String? = null
)

fun UserProfileSnapshot.toUserProfile(): UserProfile =
    UserProfile(
        address = address ?: "",
        employeeNumber = employeeNumber ?: "",
        jobTitle = jobTitle ?: ""
    )