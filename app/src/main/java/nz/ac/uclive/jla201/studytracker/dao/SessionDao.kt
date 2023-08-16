package nz.ac.uclive.jla201.studytracker.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nz.ac.uclive.jla201.studytracker.Session
import nz.ac.uclive.jla201.studytracker.Subject
import java.time.LocalDate


@Dao
interface SessionDao {
    @Query("SELECT * FROM session")
    fun getAll(): Flow<List<Session>>

    @Query("SELECT * FROM session WHERE session.id IN (:sessionIds)")
    fun loadAllByIds(sessionIds: IntArray): Flow<List<Session>>

    @Query("SELECT count(*) FROM session")
    fun getCount(): Flow<Int>

    @Query("SELECT sum(session.duration) FROM session WHERE session.subject_id = :subjectId")
    fun getTotalTimeForSubject(subjectId : Int): Flow<Float>

    @Query("SELECT sum(session.duration) FROM session WHERE session.date = :date")
    fun getTotalTimeForDate(date: Long): Flow<Int>

    @Query("DELETE FROM session WHERE session.date < :date")
    suspend fun removeBeforeDate(date: Long)

    @Insert
    suspend fun insert(session: Session)

    @Insert
    suspend fun insertAll(vararg sessions: Session)

    @Delete
    suspend fun delete(session: Session)
}