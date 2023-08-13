package nz.ac.uclive.jla201.studytracker.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nz.ac.uclive.jla201.studytracker.Session
import nz.ac.uclive.jla201.studytracker.repository.SessionRepository
import nz.ac.uclive.jla201.studytracker.repository.SubjectRepository

class SessionViewModel (private val sessionRepository: SessionRepository): ViewModel() {

    val sessions: LiveData<List<Session>> = sessionRepository.sessions.asLiveData()
    val numSessions: LiveData<Int> = sessionRepository.numSessions.asLiveData()

    fun addSession(session: Session) = viewModelScope.launch {
        sessionRepository.insert(session)
    }
}