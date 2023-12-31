package com.verindrarizya.attendancefirebase.core.data.repository.profile

import com.verindrarizya.attendancefirebase.core.entity.User
import com.verindrarizya.attendancefirebase.core.entity.UserProfile
import com.verindrarizya.attendancefirebase.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getUserData(): User

    fun getProfileData(): Flow<Resource<UserProfile>>

}