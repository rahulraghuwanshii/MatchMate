package com.rahulraghuwanshi.matchmate.data.repository

import com.rahulraghuwanshi.matchmate.data.repository.datasource.MatchMateRemoteDataSource
import com.rahulraghuwanshi.matchmate.domain.repository.MatchMateRepository
import javax.inject.Inject

class MatchMateRepositoryImpl @Inject constructor(
    private val matchMateRemoteDataSource: MatchMateRemoteDataSource
) : MatchMateRepository {
    override suspend fun fetchUserProfile(page: Int) =
        matchMateRemoteDataSource.fetchUserProfile(page)
}