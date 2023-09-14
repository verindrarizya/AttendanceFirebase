package com.verindrarizya.attendancefirebase.core.data.repository.profile

import com.verindrarizya.attendancefirebase.core.entity.User
import com.verindrarizya.attendancefirebase.core.entity.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getUserData(): User

    fun getProfileData(): Flow<com.verindrarizya.attendancefirebase.common.util.Resource<UserProfile>>

}