package com.verindrarizya.attendancefirebase.core.data.repository.office

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.verindrarizya.attendancefirebase.core.data.model.firebase.OfficeSnapshot
import com.verindrarizya.attendancefirebase.core.data.model.firebase.toOffice
import com.verindrarizya.attendancefirebase.core.entity.Office
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfficeRepositoryImpl @Inject constructor(
    firebaseDatabase: FirebaseDatabase
) : OfficeRepository {

    private val officesReference = firebaseDatabase.getReference("offices")

    override fun getOffices(): Flow<com.verindrarizya.attendancefirebase.common.util.Resource<List<Office>>> =
        callbackFlow {

            val officesValueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val officesSnapshot = snapshot.children.mapNotNull {
                        it.getValue(OfficeSnapshot::class.java)
                    }

                    val offices = officesSnapshot.map { it.toOffice() }

                    trySend(
                        com.verindrarizya.attendancefirebase.common.util.Resource.Success(
                            offices
                        )
                    )
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(com.verindrarizya.attendancefirebase.common.util.Resource.Error(error.message))
                }
            }

            officesReference.addValueEventListener(officesValueEventListener)

            awaitClose {
                officesReference.removeEventListener(officesValueEventListener)
            }
        }

}