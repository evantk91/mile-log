package com.example.milelog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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

        // DEBUG: views that visualize the systemBars insets. Remove along with the
        // block below once you're done exploring how the insets listener behaves.
        val systemBarCueTop: View = findViewById(R.id.systemBarCueTop)
        val systemBarCueBottom: View = findViewById(R.id.systemBarCueBottom)
        val systemBarCueStart: View = findViewById(R.id.systemBarCueStart)
        val systemBarCueEnd: View = findViewById(R.id.systemBarCueEnd)
        val insetsDebugText: TextView = findViewById(R.id.insetsDebugText)

        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            // Size each cue strip to the exact pixel value returned for that edge, so its
            // thickness on screen literally *is* the inset the padding above just consumed.
            (systemBarCueTop.layoutParams as ConstraintLayout.LayoutParams).height = systemBars.top
            systemBarCueTop.requestLayout()
            (systemBarCueBottom.layoutParams as ConstraintLayout.LayoutParams).height = systemBars.bottom
            systemBarCueBottom.requestLayout()
            (systemBarCueStart.layoutParams as ConstraintLayout.LayoutParams).width = systemBars.left
            systemBarCueStart.requestLayout()
            (systemBarCueEnd.layoutParams as ConstraintLayout.LayoutParams).width = systemBars.right
            systemBarCueEnd.requestLayout()

            insetsDebugText.text = "systemBars insets (px): L=${systemBars.left} T=${systemBars.top} " +
                "R=${systemBars.right} B=${systemBars.bottom}"

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