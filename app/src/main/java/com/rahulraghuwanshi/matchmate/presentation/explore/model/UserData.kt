package com.rahulraghuwanshi.matchmate.presentation.explore.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rahulraghuwanshi.matchmate.data.network.model.UserDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Entity(tableName = "userMatchProfileTables")
@Serializable
data class UserData(
    val id: Int,
    val title: String,
    val subtitle: String,
    val imageUrl: String? = null,
    val interests: List<String> = emptyList(),
    val verified: Boolean = randomBoolean(),
    val online: Boolean = randomBoolean(),
    val matched: Matched = Matched.NONE
){
    @PrimaryKey(autoGenerate = true)
    var uuId: Int = 0
}

enum class Matched {
    LIKE,DISLIKE,NONE
}

/**
 * Maps a User straight into the CardItem the adapter/layout manager expect.
 * Keeps CardAdapter decoupled from the raw API shape.
 */
fun UserDetails.toUserData(
): UserData {
    val distanceKm = Random.nextInt(1, 16)

    fun getInterests(): List<String> {
        val count = Random.nextInt(2, 4)
        return habits.shuffled().take(count)
    }
    return UserData(
        id = id,
        title = "${name?.first}, ${dob?.age}",
        subtitle = "${location?.city}, ${location?.state}" +
                (distanceKm?.let { " • $it km away" } ?: ""),
        imageUrl = picture?.large,
        interests = getInterests()
    )
}

val habits = listOf(
    "Early Bird",
    "Night Owl",
    "Coffee Lover",
    "Tea Enthusiast",
    "Gym Regular",
    "Bookworm",
    "Dog Person",
    "Foodie",
    "Travel Addict",
    "Music Lover"
)


fun randomBoolean(): Boolean = kotlin.random.Random.nextBoolean()