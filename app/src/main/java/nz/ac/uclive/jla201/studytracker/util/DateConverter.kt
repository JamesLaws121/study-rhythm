package nz.ac.uclive.jla201.studytracker.util

import androidx.room.TypeConverter
import java.time.LocalDate

class DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long): LocalDate {
        return LocalDate.ofEpochDay(dateLong)
    }

    @TypeConverter
    fun fromDate(date: LocalDate): Long {
        return date.toEpochDay()
    }
}