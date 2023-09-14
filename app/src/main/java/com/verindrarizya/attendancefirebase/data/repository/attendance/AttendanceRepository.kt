package com.verindrarizya.attendancefirebase.data.repository.attendance

import com.verindrarizya.attendancefirebase.common.state.AttendanceState
import com.verindrarizya.attendancefirebase.common.state.TodayAttendanceState
import com.verindrarizya.attendancefirebase.common.util.Resource
import com.verindrarizya.attendancefirebase.data.paging.AttendanceRecordsPagingSource
import com.verindrarizya.attendancefirebase.ui.model.Office
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {

    fun checkTodayAttendanceState(): Flow<Resource<TodayAttendanceState>>

    fun recordAttendance(
        office: Office,
        attendanceState: AttendanceState
    ): Flow<Resource<String>>

    fun getAttendanceRecordsPagingSource(
        startDate: String,
        endDate: String
    ): AttendanceRecordsPagingSource
}