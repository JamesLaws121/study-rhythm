package nz.ac.uclive.jla201.studytracker.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import nz.ac.uclive.jla201.studytracker.dao.SubjectDao
import nz.ac.uclive.jla201.studytracker.Subject

class SubjectRepository(private val subjectDao: SubjectDao) {
    val subjects: Flow<List<Subject>> = subjectDao.getAll()
    val numSubjects: Flow<Int> = subjectDao.getCount()

    @WorkerThread
    suspend fun insert(subject: Subject) {
        subjectDao.insert(subject)
    }

    @WorkerThread
    fun findById(subjectId: Int): Flow<Subject> {
        return subjectDao.findById(subjectId)
    }
}
