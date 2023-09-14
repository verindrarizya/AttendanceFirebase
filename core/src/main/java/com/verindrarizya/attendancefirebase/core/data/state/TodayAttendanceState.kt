package com.verindrarizya.attendancefirebase.core.data.state

import com.verindrarizya.attendancefirebase.core.entity.Office

sealed class TodayAttendanceState {
    object NoRecord : TodayAttendanceState()

    data class CheckedIn(val office: Office) : TodayAttendanceState()

    object CheckedOut : TodayAttendanceState()
}