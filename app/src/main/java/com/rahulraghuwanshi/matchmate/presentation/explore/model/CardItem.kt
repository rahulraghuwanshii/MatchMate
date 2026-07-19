package com.rahulraghuwanshi.matchmate.presentation.explore.model

import com.rahulraghuwanshi.matchmate.data.network.model.UserDetails
import kotlin.random.Random

data class CardItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val imageUrl: String? = null,
    val interests: List<String> = emptyList(),
    val verified: Boolean = randomBoolean(),
    val online: Boolean = randomBoolean()
)

/**
 * Maps a User straight into the CardItem the adapter/layout manager expect.
 * Keeps CardAdapter decoupled from the raw API shape.
 */
fun UserDetails.toCardItem(
): CardItem {
    val distanceKm = Random.nextInt(1, 16)

    fun getInterests(): List<String> {
        val count = Random.nextInt(2, 4)
        return habits.shuffled().take(count)
    }
    return CardItem(
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