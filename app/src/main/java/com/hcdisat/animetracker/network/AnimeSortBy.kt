package com.hcdisat.animetracker.network

enum class AnimeSortBy(val realName: String) {
    POPULAR("user_count"),
    MOST_RATED("-average_rating")
}