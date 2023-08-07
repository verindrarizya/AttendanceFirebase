package com.verindrarizya.attendancefirebase.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.verindrarizya.attendancefirebase.data.firebasemodel.OfficeSnapshot
import com.verindrarizya.attendancefirebase.data.firebasemodel.toOffice
import com.verindrarizya.attendancefirebase.ui.screens.model.Office
import com.verindrarizya.attendancefirebase.util.ResourceState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class OfficeRepository @Inject constructor(
    firebaseDatabase: FirebaseDatabase
) {

    private val officesReference = firebaseDatabase.getReference("offices")

    fun getOffices(): Flow<ResourceState<List<Office>>> = callbackFlow {

        val officesValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val officesSnapshot = snapshot.children.mapNotNull {
                    it.getValue(OfficeSnapshot::class.java)
                }

                val offices = officesSnapshot.map { it.toOffice() }

                trySend(ResourceState.Success(offices))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResourceState.Error(error.message))
            }
        }

        officesReference.addValueEventListener(officesValueEventListener)

        awaitClose {
            officesReference.removeEventListener(officesValueEventListener)
        }
    }

}