package nz.ac.uclive.jla201.studytracker.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nz.ac.uclive.jla201.studytracker.Session
import nz.ac.uclive.jla201.studytracker.repository.SessionRepository
import nz.ac.uclive.jla201.studytracker.repository.SubjectRepository
import java.time.LocalDate

class SessionViewModel (private val sessionRepository: SessionRepository): ViewModel() {

    val sessions: LiveData<List<Session>> = sessionRepository.sessions.asLiveData()
    val numSessions: LiveData<Int> = sessionRepository.numSessions.asLiveData()

    fun addSession(session: Session) = viewModelScope.launch {
        sessionRepository.insert(session)
    }

    fun calculateTimeForSubject(subjectId: Int): LiveData<Float> {
        return sessionRepository.getTotalTimeForSubject(subjectId).asLiveData()
    }

    fun calculateTimeForDate(date: Long): LiveData<Int> {
        return sessionRepository.getTotalTimeForDate(date).asLiveData()
    }

    fun removeOldSessions(date: Long) = viewModelScope.launch {
        sessionRepository.removeOldSessions(date)
    }
}