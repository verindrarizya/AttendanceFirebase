package com.verindrarizya.attendancefirebase.core.data.repository.attendance

import com.verindrarizya.attendancefirebase.core.data.paging.AttendanceRecordsPagingSource
import com.verindrarizya.attendancefirebase.core.data.state.AttendanceState
import com.verindrarizya.attendancefirebase.core.data.state.TodayAttendanceState
import com.verindrarizya.attendancefirebase.core.entity.Office
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {

    fun checkTodayAttendanceState(): Flow<com.verindrarizya.attendancefirebase.common.util.Resource<TodayAttendanceState>>

    fun recordAttendance(
        office: Office,
        attendanceState: AttendanceState
    ): Flow<com.verindrarizya.attendancefirebase.common.util.Resource<String>>

    fun getAttendanceRecordsPagingSource(
        startDate: String,
        endDate: String
    ): AttendanceRecordsPagingSource
}