package nz.ac.uclive.jla201.studytracker

import androidx.room.*
import nz.ac.uclive.jla201.studytracker.util.DateConverter
import java.time.LocalDate
import java.util.*

@Entity(tableName = "session")
data class Session (
    @PrimaryKey(autoGenerate = true) var id: Int? = null,

    @ColumnInfo(name = "description") var description: String? = null,

    @ColumnInfo(name = "date") var date: LocalDate,

    @ColumnInfo(name = "start") var start: Long? = null,

    @ColumnInfo(name = "duration") var duration: Long? = null,

    @ColumnInfo(name = "subject_id") var subjectId: Int
)