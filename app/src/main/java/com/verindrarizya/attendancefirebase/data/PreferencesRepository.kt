package com.verindrarizya.attendancefirebase.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

object DataStoreKey {
    val OnBoardedKey = booleanPreferencesKey("on_boarded")
}

@Singleton
class PreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val isUserAlreadyOnBoarded: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DataStoreKey.OnBoardedKey] ?: false
    }

    fun setUserOnBoarded() = runBlocking {
        dataStore.edit { settings ->
            settings[DataStoreKey.OnBoardedKey] = true
        }
    }
}