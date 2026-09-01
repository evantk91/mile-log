package com.example.milelog

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.milelog.data.TripRepository
import com.example.milelog.data.model.Trip
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddTripActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_trip)

        val rootView = findViewById<android.view.View>(R.id.main)
        val contentContainer = findViewById<android.view.View>(R.id.contentContainer)

        ViewCompat.setOnApplyWindowInsetsListener(rootView) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            contentContainer.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, TripListActivity::class.java)
            startActivity(intent)
            finish()
        }

        val startingLocationEditText: TextInputEditText = findViewById(R.id.startingLocationEditText)
        val endingLocationEditText: TextInputEditText = findViewById(R.id.endingLocationEditText)
        val dateEditText: TextInputEditText = findViewById(R.id.dateEditText)
        val distanceEditText: TextInputEditText = findViewById(R.id.distanceEditText)

        val saveTripButton: MaterialButton = findViewById(R.id.saveTripButton)
        saveTripButton.setOnClickListener {
            val startingLocation = startingLocationEditText.text.toString().trim()
            val endingLocation = endingLocationEditText.text.toString().trim()
            val date = dateEditText.text.toString().trim()

            if (startingLocation.isEmpty()) {
                Toast.makeText(this, R.string.starting_location_required, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (endingLocation.isEmpty()) {
                Toast.makeText(this, R.string.ending_location_required, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val trip = Trip(
                id = TripRepository.instance.generateTripId(),
                startingLocation = startingLocation,
                endingLocation = endingLocation,
                date = date.ifEmpty { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) },
                distanceMiles = distanceEditText.text.toString().toDoubleOrNull() ?: 0.0
            )
            TripRepository.instance.addTrip(trip)
            finish()
        }
    }
}
