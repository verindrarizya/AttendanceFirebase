package com.verindrarizya.attendancefirebase.core.data.model.firebase

import com.google.firebase.database.IgnoreExtraProperties
import com.verindrarizya.attendancefirebase.core.entity.AttendanceRecord
import com.verindrarizya.attendancefirebase.core.entity.Office

@IgnoreExtraProperties
data class AttendanceRecordSnapshot(
    val status: String? = null,
    val officeId: Int? = null,
    val address: String? = null,
    val officeImageUrl: String? = null,
    val officeName: String? = null,
    val date: String? = null,
    val hour: String? = null,
)

fun AttendanceRecordSnapshot.toOffice(): Office =
    Office(
        id = officeId ?: 0,
        address = address ?: "",
        name = officeName ?: "",
        imageUrl = officeImageUrl ?: ""
    )

fun AttendanceRecordSnapshot.toAttendance() =
    AttendanceRecord(
        status = status ?: "",
        officeId = officeId ?: 0,
        address = address ?: "",
        officeImageUrl = officeImageUrl ?: "",
        officeName = officeName ?: "",
        date = date ?: "",
        hour = hour ?: ""
    )