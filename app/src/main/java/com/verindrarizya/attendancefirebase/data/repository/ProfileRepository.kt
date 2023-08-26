package com.verindrarizya.attendancefirebase.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.verindrarizya.attendancefirebase.data.firebasemodel.UserProfileSnapshot
import com.verindrarizya.attendancefirebase.data.firebasemodel.toUserProfile
import com.verindrarizya.attendancefirebase.ui.model.UserProfile
import com.verindrarizya.attendancefirebase.util.ResourceState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) {

    fun getUsername(): String = auth.currentUser?.displayName ?: ""

    fun getEmail(): String = auth.currentUser?.email ?: ""

    fun getProfileData(): Flow<ResourceState<UserProfile>> = callbackFlow {
        trySend(ResourceState.Loading)

        val userProfileRef = firebaseDatabase.reference
            .child("profile")
            .child(auth.uid ?: "")

        val userProfileValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfileSnapshot = snapshot.getValue<UserProfileSnapshot>()

                if (userProfileSnapshot == null) {
                    trySend(ResourceState.Error("Data Not Found"))
                } else {
                    val userProfile = userProfileSnapshot.toUserProfile()
                    trySend(ResourceState.Success(userProfile))
                }

            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResourceState.Error(error.message))
            }
        }

        userProfileRef.addValueEventListener(userProfileValueEventListener)

        awaitClose { userProfileRef.removeEventListener(userProfileValueEventListener) }
    }

}