package com.example.milelog.data

import com.example.milelog.data.model.Trip

class TripRepository {
    private val trips = mutableListOf(
        Trip(id = 1, startingLocation = "Home", endingLocation = "Work", date = "2026-08-24", distanceMiles = 12.4),
        Trip(id = 2, startingLocation = "Work", endingLocation = "Gym", date = "2026-08-25", distanceMiles = 3.1),
        Trip(id = 3, startingLocation = "Home", endingLocation = "Airport", date = "2026-08-27", distanceMiles = 22.8)
    )

    private var nextId: Long = trips.maxOf { it.id } + 1

    fun getTripById(id: Long): Trip? = trips.find { it.id == id }

    fun getAllTrips(): List<Trip> = trips.toList()

    fun addTrip(trip: Trip) {
        trips.add(trip)
    }

    fun generateTripId(): Long = nextId++

    companion object {
        val instance: TripRepository by lazy { TripRepository() }
    }
}
