package com.verindrarizya.attendancefirebase.core.entity

import androidx.compose.runtime.Stable

@Stable
data class Office(
    val id: Int,
    val address: String,
    val imageUrl: String,
    val name: String
)