package com.rahulraghuwanshi.matchmate.data.network

import com.rahulraghuwanshi.matchmate.data.network.model.UserProfileResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchMateApiInterface {

    @GET("api/")
    suspend fun fetchUserProfile(@Query("page") page: Int): Response<UserProfileResponse?>

}