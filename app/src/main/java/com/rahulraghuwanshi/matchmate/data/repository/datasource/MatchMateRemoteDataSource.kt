package com.rahulraghuwanshi.matchmate.data.repository.datasource

import com.rahulraghuwanshi.matchmate.data.network.model.UserProfileResponse
import com.rahulraghuwanshi.matchmate.data.util.ApiResult

interface MatchMateRemoteDataSource {

    suspend fun fetchUserProfile(page: Int): ApiResult<UserProfileResponse?>

}