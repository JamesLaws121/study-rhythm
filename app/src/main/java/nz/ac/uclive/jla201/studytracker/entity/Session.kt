package nz.ac.uclive.jla201.studytracker

import androidx.room.*
import java.time.LocalDate

@Entity(tableName = "session")
data class Session (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,

    @ColumnInfo(name = "description") var description: String? = null,

    @ColumnInfo(name = "date") var date: LocalDate,

    @ColumnInfo(name = "start") var start: Long? = null,

    @ColumnInfo(name = "duration") var duration: Float? = null,

    @ColumnInfo(name = "subject_id") var subjectId: Int
)