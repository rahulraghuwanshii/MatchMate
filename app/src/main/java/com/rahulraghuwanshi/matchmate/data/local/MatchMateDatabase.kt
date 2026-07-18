package com.rahulraghuwanshi.matchmate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rahulraghuwanshi.matchmate.data.local.converters.StringListTypeConverter
import com.rahulraghuwanshi.matchmate.data.local.model.RemoteKeys
import com.rahulraghuwanshi.matchmate.data.network.model.UserDetails

@Database(
    entities = [UserDetails::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListTypeConverter::class)
abstract class MatchMateDatabase : RoomDatabase() {
    abstract fun matchMateDao(): MatchMateDao

    abstract fun remoteKeyDao(): RemoteKeysDao
}