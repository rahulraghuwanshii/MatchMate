package com.rahulraghuwanshi.matchmate.domain.use_case

import com.rahulraghuwanshi.matchmate.data.network.model.UserProfileResponse
import com.rahulraghuwanshi.matchmate.data.util.ApiResult

interface FetchUserProfileUseCase {

    suspend operator fun invoke(page: Int): ApiResult<UserProfileResponse?>
}