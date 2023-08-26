package com.verindrarizya.attendancefirebase.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.verindrarizya.attendancefirebase.data.firebasemodel.AttendanceRecordSnapshot
import com.verindrarizya.attendancefirebase.data.firebasemodel.toOffice
import com.verindrarizya.attendancefirebase.data.paging.AttendanceRecordsPagingSource
import com.verindrarizya.attendancefirebase.ui.model.Office
import com.verindrarizya.attendancefirebase.util.AttendanceState
import com.verindrarizya.attendancefirebase.util.CalendarUtils
import com.verindrarizya.attendancefirebase.util.ResourceState
import com.verindrarizya.attendancefirebase.util.TodayAttendanceState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class AttendanceRepository @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val auth: FirebaseAuth
) {

    fun checkTodayAttendanceState(): Flow<ResourceState<TodayAttendanceState>> = callbackFlow {
        trySend(ResourceState.Loading)

        val userAttendanceReference = firebaseDatabase
            .getReference("attendance/${auth.currentUser?.uid}")
            .limitToLast(1)

        val todayAttendanceStateValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val attendanceRecordSnapshots = snapshot.children.mapNotNull {
                    it.getValue(AttendanceRecordSnapshot::class.java)
                }

                if (attendanceRecordSnapshots.isEmpty()) {
                    trySend(ResourceState.Success(TodayAttendanceState.NoRecord))
                } else {
                    val record = attendanceRecordSnapshots[0]

                    if (record.date == CalendarUtils.currentDate) {
                        if (record.status == AttendanceState.CheckIn.value) {
                            val currentOffice = record.toOffice()
                            trySend(
                                ResourceState.Success(
                                    TodayAttendanceState.CheckedIn(
                                        currentOffice
                                    )
                                )
                            )
                        } else {
                            trySend(ResourceState.Success(TodayAttendanceState.CheckedOut))
                        }
                    } else {
                        trySend(ResourceState.Success(TodayAttendanceState.NoRecord))
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResourceState.Error(error.message))
            }
        }

        userAttendanceReference.addValueEventListener(todayAttendanceStateValueEventListener)

        awaitClose {
            userAttendanceReference.removeEventListener(todayAttendanceStateValueEventListener)
        }
    }

    fun recordAttendance(
        office: Office,
        attendanceState: AttendanceState
    ): Flow<ResourceState<String>> = callbackFlow {

        val attendanceRecordSnapshot = AttendanceRecordSnapshot(
            status = attendanceState.value,
            officeId = office.id,
            address = office.address,
            officeImageUrl = office.imageUrl,
            officeName = office.name,
            date = CalendarUtils.currentDate,
            hour = CalendarUtils.currentHour
        )

        val userAttendanceReference = firebaseDatabase
            .getReference("attendance")
            .child(auth.currentUser?.uid ?: "")

        userAttendanceReference.push().setValue(attendanceRecordSnapshot)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(ResourceState.Success("${attendanceState.value} Success"))
                } else {
                    trySend(
                        ResourceState.Error(
                            task.exception?.message ?: "${attendanceState.value} Failed"
                        )
                    )
                }
            }
            .addOnFailureListener {
                trySend(ResourceState.Error(it.message ?: "${attendanceState.value} Failed"))
            }

        awaitClose { }
    }

    fun getAttendanceRecordsPagingSource(
        startDate: String,
        endDate: String
    ): AttendanceRecordsPagingSource = AttendanceRecordsPagingSource(
        db = firebaseDatabase.reference,
        startDate = startDate,
        endDate = endDate,
        userUniqueId = auth.currentUser?.uid ?: ""
    )

}