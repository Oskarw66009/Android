package com.example.oj

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MinutnikActivity : AppCompatActivity() {

    private lateinit var textViewMinutnik: TextView
    private lateinit var btnStartStop: Button
    private lateinit var countDownTimer: CountDownTimer
    private var isTimerRunning = false
    private var remainingTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minutnik)

        textViewMinutnik = findViewById(R.id.textViewMinutnik)
        btnStartStop = findViewById(R.id.btnStartStop)

        btnStartStop.setOnClickListener {
            if (isTimerRunning) {
                stopMinutnik()
            } else {
                startMinutnik()
            }
        }
    }

    private fun startMinutnik() {
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                val seconds = millisUntilFinished / 1000
                textViewMinutnik.text = "Czas: $seconds s"
            }

            override fun onFinish() {
                textViewMinutnik.text = "Czas: 0 s"
                isTimerRunning = false
            }
        }.start()

        isTimerRunning = true
        btnStartStop.text = "Stop"
    }

    private fun stopMinutnik() {
        countDownTimer.cancel()
        textViewMinutnik.text = "Czas: ${remainingTime / 1000} s"
        isTimerRunning = false
        btnStartStop.text = "Start"
    }
}