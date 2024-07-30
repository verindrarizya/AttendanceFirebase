package com.verindrarizya.attendancefirebase.core.data.state

import com.verindrarizya.attendancefirebase.core.entity.Office

sealed class TodayAttendanceState {
    data object NoRecord : TodayAttendanceState()

    data class CheckedIn(val office: Office) : TodayAttendanceState()

    data object CheckedOut : TodayAttendanceState()
}