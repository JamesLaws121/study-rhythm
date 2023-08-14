package nz.ac.uclive.jla201.studytracker.util

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): LocalDate? {
        return dateLong?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate() };
    }

    @TypeConverter
    fun fromDate(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}