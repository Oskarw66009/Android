package com.example.oj

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val btnCalendar: Button = findViewById(R.id.btnCalendar)
        val btnMinutnik: Button = findViewById(R.id.btnMinutnik)
        val btnStoper: Button = findViewById(R.id.btnStoper)
        val btnAlarm: Button = findViewById(R.id.btnAlarm)
        val btnCzasSwiata: Button = findViewById(R.id.btnCzasSwiata)
        val btnExit: Button = findViewById(R.id.btnExit)

        btnCalendar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnMinutnik.setOnClickListener {
            val intent = Intent(this, MinutnikActivity::class.java)
            startActivity(intent)
        }

        btnStoper.setOnClickListener {
            val intent = Intent(this, StoperActivity::class.java)
            startActivity(intent)
        }

        btnAlarm.setOnClickListener {
            val intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
        }

        btnCzasSwiata.setOnClickListener {
            val intent = Intent(this, CzasSwiataActivity::class.java)
            startActivity(intent)
        }

        btnExit.setOnClickListener {
            // Wywołujemy funkcję finish() aby zamknąć bieżącą aktywność i wrócić do ekranu głównego urządzenia.
            finish()
        }
    }
}