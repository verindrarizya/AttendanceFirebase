package com.verindrarizya.attendancefirebase.data.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.verindrarizya.attendancefirebase.util.AuthState
import com.verindrarizya.attendancefirebase.util.ResourceState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {

    val authStateFlow: Flow<AuthState> = callbackFlow {

        val listener = AuthStateListener { p0 ->
            p0.currentUser?.let {
                trySend(AuthState.SignedIn)
            } ?: trySend(AuthState.SignedOut)
        }

        auth.addAuthStateListener(listener)

        awaitClose { auth.removeAuthStateListener(listener) }
    }

    fun register(
        email: String,
        password: String,
        fullName: String
    ): Flow<ResourceState<String>> = callbackFlow {
        trySend(ResourceState.Loading)

        val profileUpdatesListener = OnCompleteListener<Void> { task ->
            if (task.isSuccessful) {
                trySend(ResourceState.Success("Register Success"))
            } else {
                trySend(
                    ResourceState.Error(
                        task.exception?.message ?: "Register Failed"
                    )
                )
            }
        }

        val registerListener = OnCompleteListener<AuthResult> { task ->
            if (task.isSuccessful) {
                val profileUpdates = userProfileChangeRequest {
                    displayName = fullName
                }

                auth.currentUser!!.updateProfile(profileUpdates)
                    .addOnCompleteListener(profileUpdatesListener)
            } else {
                trySend(ResourceState.Error(task.exception?.message ?: "Register Failed"))
            }
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(registerListener)

        awaitClose { }
    }

    fun login(
        email: String,
        password: String,
    ): Flow<ResourceState<String>> = callbackFlow {
        trySend(ResourceState.Loading)

        val listener = OnCompleteListener<AuthResult> {
            if (it.isSuccessful) {
                trySend(ResourceState.Success("Login Success"))
            } else {
                trySend(ResourceState.Error(it.exception?.message ?: "Login Failed"))
            }
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener)

        awaitClose { }
    }

    fun signOut() {
        auth.signOut()
    }
}