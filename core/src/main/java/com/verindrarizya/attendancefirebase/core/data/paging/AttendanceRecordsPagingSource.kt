package com.verindrarizya.attendancefirebase.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.database.DatabaseReference
import com.verindrarizya.attendancefirebase.core.data.model.firebase.toAttendance
import com.verindrarizya.attendancefirebase.core.entity.AttendanceRecord
import com.verindrarizya.attendancefirebase.core.util.CalendarUtils
import kotlinx.coroutines.tasks.await

class AttendanceRecordsPagingSource(
    private val db: DatabaseReference,
    private val startDate: String,
    private val endDate: String,
    private val userUniqueId: String
) : PagingSource<String, AttendanceRecord>() {

    override fun getRefreshKey(state: PagingState<String, AttendanceRecord>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, AttendanceRecord> {
        try {
            val currentEndDate = params.key ?: endDate

            val queryAttendanceRecord = db
                .child("attendance/$userUniqueId")
                .orderByChild("date")
                .startAt(currentEndDate)
                .endAt(startDate)
                .limitToLast(10)

            val attendanceRecordsSnapshot = queryAttendanceRecord.get().await()
            val attendanceRecords = attendanceRecordsSnapshot.children.mapNotNull {
                it.getValue(com.verindrarizya.attendancefirebase.core.data.model.firebase.AttendanceRecordSnapshot::class.java)
            }.map {
                it.toAttendance()
            }.reversed()

            val lastAttendanceRecordDate = attendanceRecords.last().date
            val nextKey =
                CalendarUtils.getPreviousDayDate(
                    lastAttendanceRecordDate
                )

            return LoadResult.Page(
                data = attendanceRecords,
                prevKey = null,
                nextKey = if (startDate == currentEndDate || nextKey == params.key) null else nextKey
            )
        } catch (e: NoSuchElementException) {
            return LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}