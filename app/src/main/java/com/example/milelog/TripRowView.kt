package com.example.milelog

import android.view.View
import android.widget.TextView
import com.example.milelog.data.model.Trip

fun View.bindTrip(trip: Trip) {
    findViewById<TextView>(R.id.tripRowStartLocationText).text = trip.startingLocation
    findViewById<TextView>(R.id.tripRowEndLocationText).text = trip.endingLocation
    findViewById<TextView>(R.id.tripRowDateText).text = trip.date
    findViewById<TextView>(R.id.tripRowDistanceText).text =
        resources.getString(R.string.trip_row_distance_format, trip.distanceMiles)
}
