package nz.ac.uclive.jla201.studytracker

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import nz.ac.uclive.jla201.studytracker.repository.SessionRepository
import nz.ac.uclive.jla201.studytracker.repository.SubjectRepository
import nz.ac.uclive.jla201.studytracker.repository.PreferencesRepository

class StudyTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        val database by lazy { StudyDatabase.getDatabase(this) }
        val subjectRepository by lazy { SubjectRepository(database.subjectDao()) }
        val sessionRepository by lazy { SessionRepository(Companion.database.sessionDao()) }
        val preferencesRepository by lazy { PreferencesRepository(appContext) }
    }

    companion object {
        lateinit var appContext: Context
        val database by lazy { StudyDatabase.getDatabase(appContext) }
        val subjectRepository by lazy { SubjectRepository(database.subjectDao()) }
        val sessionRepository by lazy { SessionRepository(database.sessionDao()) }
        val Context.userPreferences: DataStore<Preferences> by preferencesDataStore(
            name = "user"
        )
        val preferencesRepository by lazy { PreferencesRepository(appContext) }
    }
}
