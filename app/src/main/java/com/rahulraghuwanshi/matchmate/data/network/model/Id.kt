package com.rahulraghuwanshi.matchmate.data.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Id(
    @SerialName("name")
    val name: String? = null,
    @SerialName("value")
    val value: String? = null
)