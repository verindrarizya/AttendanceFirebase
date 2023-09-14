package com.verindrarizya.attendancefirebase.data.repository.profile

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.verindrarizya.attendancefirebase.common.util.Resource
import com.verindrarizya.attendancefirebase.data.model.firebase.UserProfileSnapshot
import com.verindrarizya.attendancefirebase.data.model.firebase.toUserProfile
import com.verindrarizya.attendancefirebase.ui.model.User
import com.verindrarizya.attendancefirebase.ui.model.UserProfile
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : ProfileRepository {

    override fun getUserData() = User(
        username = auth.currentUser?.displayName ?: "",
        email = auth.currentUser?.email ?: ""
    )

    override fun getProfileData(): Flow<Resource<UserProfile>> = callbackFlow {
        trySend(Resource.Loading)

        val userProfileRef = firebaseDatabase.reference
            .child("profile")
            .child(auth.uid ?: "")

        val userProfileValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfileSnapshot = snapshot.getValue<UserProfileSnapshot>()

                if (userProfileSnapshot == null) {
                    trySend(Resource.Error("Data Not Found"))
                } else {
                    val userProfile = userProfileSnapshot.toUserProfile()
                    trySend(Resource.Success(userProfile))
                }

            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resource.Error(error.message))
            }
        }

        userProfileRef.addValueEventListener(userProfileValueEventListener)

        awaitClose { userProfileRef.removeEventListener(userProfileValueEventListener) }
    }

}