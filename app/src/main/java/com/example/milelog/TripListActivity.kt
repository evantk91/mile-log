package com.example.milelog

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.milelog.data.TripRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TripListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_trip_list)

        val rootView = findViewById<android.view.View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val addTripFab: FloatingActionButton = findViewById(R.id.addTripFab)
        addTripFab.setOnClickListener {
            val intent = Intent(this, AddTripActivity::class.java)
            startActivity(intent)
        }

        val tripListContainer: LinearLayout = findViewById(R.id.tripListContainer)
        for (trip in TripRepository.instance.getAllTrips()) {
            val tripTextView = TextView(this)
            tripTextView.text = "${trip.startingLocation} -> ${trip.endingLocation} on ${trip.date}, ${trip.distanceMiles} mi"
            tripListContainer.addView(tripTextView)
        }
    }
}