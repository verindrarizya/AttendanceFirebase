package com.verindrarizya.attendancefirebase.common.state

import com.verindrarizya.attendancefirebase.ui.model.Office

sealed class TodayAttendanceState {
    object NoRecord : TodayAttendanceState()

    data class CheckedIn(val office: Office) : TodayAttendanceState()

    object CheckedOut : TodayAttendanceState()
}