package com.rahulraghuwanshi.matchmate.domain.repository

import com.rahulraghuwanshi.matchmate.data.network.model.UserProfileResponse
import com.rahulraghuwanshi.matchmate.data.util.ApiResult

interface MatchMateRepository {

    suspend fun fetchUserProfile(page: Int): ApiResult<UserProfileResponse?>

}