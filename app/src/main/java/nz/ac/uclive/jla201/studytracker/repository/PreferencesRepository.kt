package nz.ac.uclive.jla201.studytracker.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nz.ac.uclive.jla201.studytracker.StudyTrackerApplication.Companion.userPreferences

class PreferencesRepository(private val context: Context) {
    private val deleteSessionsKey = intPreferencesKey("delete_session")
    private val sessionLifeKey = intPreferencesKey("session_session")

    val deleteSession: Flow<Int> = context.userPreferences.data.map { preferences ->
        preferences[deleteSessionsKey] ?: 0
    }

    val sessionLife: Flow<Int> = context.userPreferences.data.map { preferences ->
        preferences[sessionLifeKey] ?: 0
    }

    suspend fun saveDeletePreferences(newPreference : Int) {
        context.userPreferences.edit { preferences ->
            preferences[deleteSessionsKey] = newPreference
        }
    }

    suspend fun saveSessionLife(newPreference : Int) {
        context.userPreferences.edit { preferences ->
            preferences[sessionLifeKey] = newPreference
        }
    }
}