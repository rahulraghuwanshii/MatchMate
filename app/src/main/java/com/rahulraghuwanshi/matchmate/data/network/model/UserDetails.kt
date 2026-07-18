package com.rahulraghuwanshi.matchmate.data.network.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "userProfileTables")
@Serializable
data class UserDetails(
    @SerialName("cell")
    val cell: String? = null,
    @SerialName("dob")
    val dob: Dob? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("gender")
    val gender: String? = null,
    @SerialName("id")
    val userId: Id? = null,
    @SerialName("location")
    val location: Location? = null,
    @SerialName("login")
    val login: Login? = null,
    @SerialName("name")
    val name: Name? = null,
    @SerialName("nat")
    val nat: String? = null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("picture")
    val picture: Picture? = null,
    @SerialName("registered")
    val registered: Registered? = null
) {
    @SerialName("userId")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}