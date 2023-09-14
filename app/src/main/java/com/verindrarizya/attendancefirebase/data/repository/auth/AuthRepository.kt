package com.verindrarizya.attendancefirebase.data.repository.auth

import com.verindrarizya.attendancefirebase.common.state.AuthState
import com.verindrarizya.attendancefirebase.common.util.Resource
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

    fun signOut(): Unit

}