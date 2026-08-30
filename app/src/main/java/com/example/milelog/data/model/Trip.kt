package com.example.milelog.data.model

data class Trip(
    val id: Long,
    val startingLocation: String,
    val endingLocation: String,
    val date: String,
    val distanceMiles: Double
)
