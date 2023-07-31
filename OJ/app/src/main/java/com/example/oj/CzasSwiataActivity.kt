package com.example.oj

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CzasSwiataActivity : AppCompatActivity() {

    private lateinit var btnShowTime: Button
    private lateinit var textViewTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_czas_swiata)

        btnShowTime = findViewById(R.id.btnShowTime)
        textViewTime = findViewById(R.id.textViewTime)

        btnShowTime.setOnClickListener {
            showTime()
        }
    }

    private fun showTime() {
        val timeNewYork = "12:00 PM"
        val timeLondon = "5:00 PM"
        val timeTokyo = "2:00 AM"

        // Wyświetlenie czasu dla różnych stref czasowych w TextView
        textViewTime.text = "Czas w Nowym Jorku: $timeNewYork\n" +
                "Czas w Londynie: $timeLondon\n" +
                "Czas w Tokio: $timeTokyo"
    }
}