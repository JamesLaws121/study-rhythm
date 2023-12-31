package nz.ac.uclive.jla201.studytracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subject")
data class Subject (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,

    @ColumnInfo(name = "name") var name: String,

    @ColumnInfo(name = "description") var description: String? = null
)