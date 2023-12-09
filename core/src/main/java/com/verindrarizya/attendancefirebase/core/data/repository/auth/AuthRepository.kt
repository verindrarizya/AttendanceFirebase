package com.verindrarizya.attendancefirebase.core.data.repository.auth

import com.verindrarizya.attendancefirebase.core.data.state.AuthState
import com.verindrarizya.attendancefirebase.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun currentAuthState(): Flow<AuthState>

    fun register(
        email: String,
        password: String,
        fullName: String
    ): Flow<Resource<String>>

    fun login(
        email: String,
        password: String
    ): Flow<Resource<String>>

    fun signOut()

}