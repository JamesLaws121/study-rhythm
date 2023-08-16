package nz.ac.uclive.jla201.studytracker.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nz.ac.uclive.jla201.studytracker.Subject


@Dao
interface SubjectDao {
    @Query("SELECT * FROM subject")
    fun getAll(): Flow<List<Subject>>

    @Query("SELECT * FROM subject WHERE subject.id IN (:subjectIds)")
    fun loadAllByIds(subjectIds: IntArray): Flow<List<Subject>>

    @Query("SELECT * FROM subject WHERE subject.id = :subjectId")
    fun findById(subjectId: Int): Flow<Subject>

    @Query("SELECT count(*) FROM subject")
    fun getCount(): Flow<Int>

    @Insert
    suspend fun insert(subject: Subject)

    @Insert
    suspend fun insertAll(vararg subjects: Subject)

    @Delete
    suspend fun delete(subject: Subject)
}