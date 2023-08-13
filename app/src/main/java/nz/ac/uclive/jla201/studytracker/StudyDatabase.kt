package nz.ac.uclive.jla201.studytracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import nz.ac.uclive.jla201.studytracker.dao.SessionDao
import nz.ac.uclive.jla201.studytracker.dao.SubjectDao

@Database(entities = [Subject::class, Session::class], version = 1)
abstract class StudyDatabase : RoomDatabase(){
    abstract fun subjectDao(): SubjectDao
    abstract fun sessionDao(): SessionDao
    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: StudyDatabase? = null

        fun getDatabase(context: Context): StudyDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudyDatabase::class.java,
                    "study_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}