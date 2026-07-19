package com.rahulraghuwanshi.matchmate.di.db

import android.content.Context
import androidx.room.Room
import com.rahulraghuwanshi.matchmate.data.local.MatchMateDatabase
import com.rahulraghuwanshi.matchmate.data.util.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    /**
     * The method returns the Database object
     * */
    @Provides
    @Singleton
    internal fun provideDatabase(@ApplicationContext context: Context): MatchMateDatabase {
        return Room.databaseBuilder(
            context,
            MatchMateDatabase::class.java,
            AppConstants.DATABASE_NAME
        )
            .build()
    }
}