package nz.ac.uclive.jla201.studytracker.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nz.ac.uclive.jla201.studytracker.Session
import nz.ac.uclive.jla201.studytracker.Subject


@Dao
interface SessionDao {
    @Query("SELECT * FROM session")
    fun getAll(): Flow<List<Session>>

    @Query("SELECT * FROM session WHERE session.id IN (:sessionIds)")
    fun loadAllByIds(sessionIds: IntArray): Flow<List<Session>>

    @Query("SELECT count(*) FROM session")
    fun getCount(): Flow<Int>

    @Query("SELECT sum(session.duration) FROM session WHERE session.subject_id = :subjectId")
    fun getTotalTimeForSubject(subjectId : Long): Long

    @Insert
    suspend fun insert(session: Session)

    @Insert
    suspend fun insertAll(vararg sessions: Session)

    @Delete
    suspend fun delete(session: Session)
}