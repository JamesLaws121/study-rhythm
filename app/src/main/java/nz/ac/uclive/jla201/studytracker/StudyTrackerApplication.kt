package nz.ac.uclive.jla201.studytracker

import android.app.Application
import android.content.Context
import nz.ac.uclive.jla201.studytracker.repository.SessionRepository
import nz.ac.uclive.jla201.studytracker.repository.SubjectRepository

class StudyTrackerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        val database by lazy { StudyDatabase.getDatabase(this) }
        val subjectRepository by lazy { SubjectRepository(database.subjectDao()) }
        val sessionRepository by lazy { SessionRepository(Companion.database.sessionDao()) }
    }

    companion object {
        lateinit  var appContext: Context
        val database by lazy { StudyDatabase.getDatabase(appContext) }
        val subjectRepository by lazy { SubjectRepository(database.subjectDao()) }
        val sessionRepository by lazy { SessionRepository(database.sessionDao()) }
    }
}
