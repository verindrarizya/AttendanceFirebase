package com.verindrarizya.attendancefirebase.di

import com.verindrarizya.attendancefirebase.data.repository.attendance.AttendanceRepository
import com.verindrarizya.attendancefirebase.data.repository.attendance.AttendanceRepositoryImpl
import com.verindrarizya.attendancefirebase.data.repository.auth.AuthRepository
import com.verindrarizya.attendancefirebase.data.repository.auth.AuthRepositoryImpl
import com.verindrarizya.attendancefirebase.data.repository.office.OfficeRepository
import com.verindrarizya.attendancefirebase.data.repository.office.OfficeRepositoryImpl
import com.verindrarizya.attendancefirebase.data.repository.preferences.PreferencesRepository
import com.verindrarizya.attendancefirebase.data.repository.preferences.PreferencesRepositoryImpl
import com.verindrarizya.attendancefirebase.data.repository.profile.ProfileRepository
import com.verindrarizya.attendancefirebase.data.repository.profile.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideOfficeRepository(officeRepositoryImpl: OfficeRepositoryImpl): OfficeRepository

    @Binds
    abstract fun providePreferencesRepository(preferencesRepositoryImpl: PreferencesRepositoryImpl): PreferencesRepository

    @Binds
    abstract fun provideProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideAttendanceRepository(attendanceRepositoryImpl: AttendanceRepositoryImpl): AttendanceRepository
}