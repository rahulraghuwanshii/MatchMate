package com.rahulraghuwanshi.matchmate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rahulraghuwanshi.matchmate.data.local.converters.StringListTypeConverter
import com.rahulraghuwanshi.matchmate.data.local.converters.type.DobConverter
import com.rahulraghuwanshi.matchmate.data.local.converters.type.IdConverter
import com.rahulraghuwanshi.matchmate.data.local.converters.type.LocationConverter
import com.rahulraghuwanshi.matchmate.data.local.converters.type.LoginConverter
import com.rahulraghuwanshi.matchmate.data.local.converters.type.NameConverter
import com.rahulraghuwanshi.matchmate.data.local.converters.type.PictureConverter
import com.rahulraghuwanshi.matchmate.data.local.converters.type.RegisteredConverter
import com.rahulraghuwanshi.matchmate.data.local.model.RemoteKeys
import com.rahulraghuwanshi.matchmate.data.network.model.UserDetails
import com.rahulraghuwanshi.matchmate.presentation.explore.model.UserData

@Database(
    entities = [UserDetails::class, RemoteKeys::class,UserData::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    StringListTypeConverter::class,
    NameConverter::class,
    LocationConverter::class,
    PictureConverter::class,
    LoginConverter::class,
    RegisteredConverter::class,
    DobConverter::class,
    IdConverter::class
)
abstract class MatchMateDatabase : RoomDatabase() {
    abstract fun matchMateDao(): MatchMateDao

    abstract fun remoteKeyDao(): RemoteKeysDao
}