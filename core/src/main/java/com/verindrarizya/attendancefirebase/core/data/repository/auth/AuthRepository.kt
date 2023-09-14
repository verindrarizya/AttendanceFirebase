package com.verindrarizya.attendancefirebase.core.data.repository.auth

import com.verindrarizya.attendancefirebase.core.data.state.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun currentAuthState(): Flow<AuthState>

    fun register(
        email: String,
        password: String,
        fullName: String
    ): Flow<com.verindrarizya.attendancefirebase.common.util.Resource<String>>

    fun login(
        email: String,
        password: String
    ): Flow<com.verindrarizya.attendancefirebase.common.util.Resource<String>>

    fun signOut()

}