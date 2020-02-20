package dev.chu.memo.data.local

import androidx.room.TypeConverter
import java.util.*

class ConverterDate {
    @TypeConverter
    fun toDate(value: Long): Date = Date(value)

    @TypeConverter
    fun toLong(date: Date): Long = date.time
}