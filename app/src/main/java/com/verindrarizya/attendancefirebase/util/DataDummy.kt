package com.verindrarizya.attendancefirebase.util

import com.verindrarizya.attendancefirebase.ui.screens.model.AttendanceRecord

object DataDummy {
    val attendanceRecords: List<AttendanceRecord> = (1..10).map {
        AttendanceRecord(
            status = "Status $it",
            officeId = 1,
            address = "Address $it",
            officeImageUrl = "https://docs.google.com/uc?id=1QLUw3n7tpZt5iV7RxwzgIwgi6w1Jo29Q",
            officeName = "Office Name $it",
            date = "01-01-2020",
            hour = "12:00"
        )
    }
}