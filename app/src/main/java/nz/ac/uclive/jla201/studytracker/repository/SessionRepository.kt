package nz.ac.uclive.jla201.studytracker.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import nz.ac.uclive.jla201.studytracker.Session
import nz.ac.uclive.jla201.studytracker.dao.SubjectDao
import nz.ac.uclive.jla201.studytracker.Subject
import nz.ac.uclive.jla201.studytracker.dao.SessionDao

class SessionRepository(private val sessionDao: SessionDao) {
    val sessions: Flow<List<Session>> = sessionDao.getAll()
    val numSessions: Flow<Int> = sessionDao.getCount()

    @WorkerThread
    suspend fun insert(session: Session) {
        sessionDao.insert(session)
    }

    @WorkerThread
    fun getTotalTimeForSubject(subjectId: Int): Flow<Int> {
        return sessionDao.getTotalTimeForSubject(subjectId)
    }
}
