package com.rahulraghuwanshi.matchmate.data.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Info(
    @SerialName("page")
    val page: Int? = null,
    @SerialName("results")
    val results: Int? = null,
    @SerialName("seed")
    val seed: String? = null,
    @SerialName("version")
    val version: String? = null
)