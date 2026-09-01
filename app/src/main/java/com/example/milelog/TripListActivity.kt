package com.example.milelog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.milelog.data.TripRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TripListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scrim = ContextCompat.getColor(this, R.color.system_bar_scrim)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.dark(scrim)
        )
        setContentView(R.layout.activity_trip_list)

        val rootView = findViewById<android.view.View>(R.id.main)
        val headerTitle: TextView = findViewById(R.id.headerTitle)
        val tripScroll = findViewById<android.view.View>(R.id.tripScroll)
        val addTripFab: FloatingActionButton = findViewById(R.id.addTripFab)

        // Base margins from the XML (24dp), captured once so we can add the dynamic
        // inset on top of them instead of overwriting the designed spacing.
        val headerBaseMargin = (headerTitle.layoutParams as ConstraintLayout.LayoutParams).topMargin
        val fabBaseMargin = (addTripFab.layoutParams as ConstraintLayout.LayoutParams).bottomMargin

        ViewCompat.setOnApplyWindowInsetsListener(rootView) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Keep the fixed header/FAB clear of the bars...
            (headerTitle.layoutParams as ConstraintLayout.LayoutParams).apply {
                topMargin = headerBaseMargin + systemBars.top
                marginStart = headerBaseMargin + systemBars.left
            }
            headerTitle.requestLayout()
            (addTripFab.layoutParams as ConstraintLayout.LayoutParams).apply {
                bottomMargin = fabBaseMargin + systemBars.bottom
                marginEnd = fabBaseMargin + systemBars.right
            }
            addTripFab.requestLayout()

            // ...but let the scrollable trip list itself extend behind the (translucent)
            // bars, with clipToPadding=false so rows are visible sliding underneath them.
            tripScroll.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)

            insets
        }

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