package com.verindrarizya.attendancefirebase.core.data.repository.preferences

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
class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    override fun isAlreadyOnBoarded(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DataStoreKey.OnBoardedKey] ?: false
    }

    override fun setOnBoarded() {
        runBlocking {
            dataStore.edit { settings ->
                settings[DataStoreKey.OnBoardedKey] = true
            }
        }
    }
}