package nz.ac.uclive.jla201.studytracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime
import java.util.Date

@Entity(tableName = "session", foreignKeys = arrayOf(ForeignKey(entity = Subject::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("id"),
    onDelete = ForeignKey.CASCADE)))
data class Session (
    @PrimaryKey(autoGenerate = true) var id: Int? = null,

    @ColumnInfo(name = "name") var name: String? = null,

    @ColumnInfo(name = "description") var description: String? = null,

    @ColumnInfo(name = "start") var start: String? = null,

    @ColumnInfo(name = "end") var end: String? = null,

    @ColumnInfo(name = "subject_id") var subjectId: Int? = null
)