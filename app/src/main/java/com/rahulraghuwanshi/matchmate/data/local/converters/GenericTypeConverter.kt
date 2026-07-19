package com.rahulraghuwanshi.matchmate.data.local.converters

import androidx.room.TypeConverter
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

abstract class GenericTypeConverter<T>(
    private val serializer: KSerializer<T>
) {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @TypeConverter
    fun fromObject(value: T?): String? {
        return value?.let {
            json.encodeToString(serializer, it)
        }
    }

    @TypeConverter
    fun toObject(value: String?): T? {
        return value?.let {
            json.decodeFromString(serializer, it)
        }
    }
}