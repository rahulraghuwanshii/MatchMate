package com.rahulraghuwanshi.matchmate.data.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    @SerialName("info")
    val info: Info? = null,
    @SerialName("results")
    val results: List<Result>? = null
)