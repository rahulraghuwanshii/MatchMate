package com.rahulraghuwanshi.matchmate.data.local.converters

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

object StringListTypeConverter {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        return value?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList()
    }

    @TypeConverter
    fun fromArrayList(list: List<String>): String {
        return Json.encodeToString(list)
    }
}