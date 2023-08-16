package nz.ac.uclive.jla201.studytracker.view_model

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import nz.ac.uclive.jla201.studytracker.StudyTrackerApplication.Companion.userPreferences
import nz.ac.uclive.jla201.studytracker.repository.PreferencesRepository

class PreferencesViewModel(private val preferencesRepository: PreferencesRepository) : ViewModel(){

    fun getDeletePreferences(): LiveData<Int> {
        return preferencesRepository.deleteSession.asLiveData()
    }

    fun getSessionLife(): LiveData<Int> {
        return preferencesRepository.sessionLife.asLiveData()
    }

    @WorkerThread
    fun saveDeletePreferences(newPreference : Int) = viewModelScope.launch {
        preferencesRepository.saveDeletePreferences(newPreference)
    }


    @WorkerThread
    fun saveSessionLife(newPreference : Int) = viewModelScope.launch {
        preferencesRepository.saveSessionLife(newPreference)
    }
}