package com.example.milelog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
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
        val inflater = LayoutInflater.from(this)
        for (trip in TripRepository.instance.getAllTrips()) {
            val tripRow = inflater.inflate(R.layout.item_trip_row, tripListContainer, false)
            tripRow.bindTrip(trip)
            tripListContainer.addView(tripRow)
        }
    }
}