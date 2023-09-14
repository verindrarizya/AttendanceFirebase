package com.verindrarizya.attendancefirebase.data.repository.attendance

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.verindrarizya.attendancefirebase.common.state.AttendanceState
import com.verindrarizya.attendancefirebase.common.state.TodayAttendanceState
import com.verindrarizya.attendancefirebase.common.util.CalendarUtils
import com.verindrarizya.attendancefirebase.common.util.Resource
import com.verindrarizya.attendancefirebase.data.model.firebase.AttendanceRecordSnapshot
import com.verindrarizya.attendancefirebase.data.model.firebase.toOffice
import com.verindrarizya.attendancefirebase.data.paging.AttendanceRecordsPagingSource
import com.verindrarizya.attendancefirebase.ui.model.Office
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class AttendanceRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val auth: FirebaseAuth
) : AttendanceRepository {

    override fun checkTodayAttendanceState(): Flow<Resource<TodayAttendanceState>> = callbackFlow {
        trySend(Resource.Loading)

        val userAttendanceReference = firebaseDatabase
            .getReference("attendance/${auth.currentUser?.uid}")
            .limitToLast(1)

        val todayAttendanceStateValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val attendanceRecordSnapshots = snapshot.children.mapNotNull {
                    it.getValue(AttendanceRecordSnapshot::class.java)
                }

                if (attendanceRecordSnapshots.isEmpty()) {
                    trySend(Resource.Success(TodayAttendanceState.NoRecord))
                } else {
                    val record = attendanceRecordSnapshots[0]

                    if (record.date == CalendarUtils.currentDate) {
                        if (record.status == AttendanceState.CheckIn.value) {
                            val currentOffice = record.toOffice()
                            trySend(
                                Resource.Success(
                                    TodayAttendanceState.CheckedIn(
                                        currentOffice
                                    )
                                )
                            )
                        } else {
                            trySend(Resource.Success(TodayAttendanceState.CheckedOut))
                        }
                    } else {
                        trySend(Resource.Success(TodayAttendanceState.NoRecord))
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resource.Error(error.message))
            }
        }

        userAttendanceReference.addValueEventListener(todayAttendanceStateValueEventListener)

        awaitClose {
            userAttendanceReference.removeEventListener(todayAttendanceStateValueEventListener)
        }
    }

    override fun recordAttendance(
        office: Office,
        attendanceState: AttendanceState
    ): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.Loading)

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
                    trySend(Resource.Success("${attendanceState.value} Success"))
                } else {
                    trySend(
                        Resource.Error(
                            task.exception?.message ?: "${attendanceState.value} Failed"
                        )
                    )
                }
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.message ?: "${attendanceState.value} Failed"))
            }

        awaitClose { }
    }

    override fun getAttendanceRecordsPagingSource(
        startDate: String,
        endDate: String
    ): AttendanceRecordsPagingSource = AttendanceRecordsPagingSource(
        db = firebaseDatabase.reference,
        startDate = startDate,
        endDate = endDate,
        userUniqueId = auth.currentUser?.uid ?: ""
    )

}