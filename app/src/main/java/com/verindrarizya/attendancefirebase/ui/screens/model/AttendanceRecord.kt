package com.verindrarizya.attendancefirebase.ui.screens.model

data class AttendanceRecord(
    val status: String,
    val officeId: Int,
    val address: String,
    val officeImageUrl: String,
    val officeName: String,
    val date: String,
    val hour: String,
)