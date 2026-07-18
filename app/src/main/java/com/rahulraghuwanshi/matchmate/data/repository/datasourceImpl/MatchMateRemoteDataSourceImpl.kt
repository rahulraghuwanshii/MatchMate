package com.rahulraghuwanshi.matchmate.data.repository.datasourceImpl

import com.rahulraghuwanshi.matchmate.data.network.MatchMateApiInterface
import com.rahulraghuwanshi.matchmate.data.network.model.UserProfileResponse
import com.rahulraghuwanshi.matchmate.data.repository.datasource.MatchMateRemoteDataSource
import com.rahulraghuwanshi.matchmate.data.util.ApiResult
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class MatchMateRemoteDataSourceImpl @Inject constructor(
    private val matchMateApiInterface: MatchMateApiInterface
) : MatchMateRemoteDataSource {

    private suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): ApiResult<T> =
        try {
            val response = apiCall()

            if (response.isSuccessful) {
                response.body()?.let { body ->
                    ApiResult.Success(body)
                } ?: ApiResult.Error("Response body is empty")
            } else {
                ApiResult.Error(
                    response.errorBody()?.string().orEmpty()
                        .ifBlank { response.message() }
                )
            }
        } catch (e: IOException) {
            ApiResult.Error("Please check your internet connection.")
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Something went wrong.")
        }

    override suspend fun fetchUserProfile(page: Int): ApiResult<UserProfileResponse?> =
        safeApiCall {
            matchMateApiInterface.fetchUserProfile(page)
        }
}