package com.rahulraghuwanshi.matchmate.di

import com.rahulraghuwanshi.matchmate.data.repository.MatchMateRepositoryImpl
import com.rahulraghuwanshi.matchmate.data.repository.datasource.MatchMateRemoteDataSource
import com.rahulraghuwanshi.matchmate.data.repository.datasourceImpl.MatchMateRemoteDataSourceImpl
import com.rahulraghuwanshi.matchmate.domain.repository.MatchMateRepository
import com.rahulraghuwanshi.matchmate.domain.use_case.FetchUserProfileUseCase
import com.rahulraghuwanshi.matchmate.domain.use_case.impl.FetchUserProfileUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MatchMateModule {

//    @Provides
//    @Singleton
//    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
//        return NoteRepositoryImpl(db.noteDao)
//    }

    @Binds
    @Singleton
    abstract fun bindMatchMateRemoteDataSource(matchMateRemoteDataSourceImpl: MatchMateRemoteDataSourceImpl): MatchMateRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMatchMateRepository(matchMateRepositoryImpl: MatchMateRepositoryImpl): MatchMateRepository

    @Binds
    @Singleton
    abstract fun bindFetchUserProfileUseCase(fetchUserProfileUseCaseImpl: FetchUserProfileUseCaseImpl): FetchUserProfileUseCase
}