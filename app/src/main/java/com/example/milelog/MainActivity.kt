package com.example.milelog

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val rootView = findViewById<android.view.View>(R.id.main)
        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
        val bottomNavigation = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation)

        // DEBUG: views that visualize the systemBars insets. Remove along with the
        // block below once you're done exploring how the insets listener behaves.
        val systemBarCueTop: View = findViewById(R.id.systemBarCueTop)
        val systemBarCueBottom: View = findViewById(R.id.systemBarCueBottom)
        val systemBarCueStart: View = findViewById(R.id.systemBarCueStart)
        val systemBarCueEnd: View = findViewById(R.id.systemBarCueEnd)
        val insetsDebugText: TextView = findViewById(R.id.insetsDebugText)

        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            
            // Apply top padding to toolbar and bottom padding to bottom navigation
            toolbar.setPadding(0, systemBars.top, 0, 0)
            bottomNavigation.setPadding(0, 0, 0, systemBars.bottom)

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

        val getStartedButton: MaterialButton = findViewById(R.id.getStartedButton)
        getStartedButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}