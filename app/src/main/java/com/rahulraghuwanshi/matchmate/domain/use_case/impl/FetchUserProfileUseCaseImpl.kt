package com.rahulraghuwanshi.matchmate.domain.use_case.impl

import com.rahulraghuwanshi.matchmate.domain.repository.MatchMateRepository
import com.rahulraghuwanshi.matchmate.domain.use_case.FetchUserProfileUseCase
import javax.inject.Inject

class FetchUserProfileUseCaseImpl @Inject constructor(
    private val matchMateRepository: MatchMateRepository
) : FetchUserProfileUseCase {
    override suspend fun invoke(page: Int) = matchMateRepository.fetchUserProfile(page)
}