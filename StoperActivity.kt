package com.example.oj

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity

class StoperActivity : AppCompatActivity() {

    private lateinit var chronometer: Chronometer
    private lateinit var btnStartStop: Button
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stoper)

        chronometer = findViewById(R.id.chronometer)
        btnStartStop = findViewById(R.id.btnStartStop)

        btnStartStop.setOnClickListener {
            if (isRunning) {
                stopStoper()
            } else {
                startStoper()
            }
        }
    }

    private fun startStoper() {
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()
        isRunning = true
        btnStartStop.text = "Stop"
    }

    private fun stopStoper() {
        chronometer.stop()
        isRunning = false
        btnStartStop.text = "Start"
    }
}
