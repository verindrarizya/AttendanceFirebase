package com.verindrarizya.attendancefirebase.data.repository.profile

import com.verindrarizya.attendancefirebase.common.util.Resource
import com.verindrarizya.attendancefirebase.ui.model.User
import com.verindrarizya.attendancefirebase.ui.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getUserData(): User

    fun getProfileData(): Flow<Resource<UserProfile>>

}