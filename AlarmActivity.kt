package com.example.oj

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class AlarmActivity : AppCompatActivity() {

    private lateinit var textViewStatus: TextView
    private lateinit var btnSetAlarm: Button
    private lateinit var btnStartAlarm: Button
    private lateinit var timePicker: TimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        textViewStatus = findViewById(R.id.textViewStatus)
        btnSetAlarm = findViewById(R.id.btnSetAlarm)
        btnStartAlarm = findViewById(R.id.btnStartAlarm)
        timePicker = findViewById(R.id.timePicker)

        btnSetAlarm.setOnClickListener {
            setAlarm()
        }

        btnStartAlarm.setOnClickListener {
            startAlarm()
        }
    }

    private fun setAlarm() {
        val hour = timePicker.hour
        val minute = timePicker.minute

        // Zapis wybranej godziny i min do----------------------------------------------
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val alarmTime = calendar.time

        // formatowanie godziny----------------------------------------------
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        val selectedTime = format.format(alarmTime)

        textViewStatus.text = "Alarm ustawiony na: $selectedTime"
    }

    private fun startAlarm() {
        // Symulacja uruchomienia alarmu----------------------------------------------
        textViewStatus.text = "Alarm włączony!"
        // przyszłość----------------------------------------------
    }
}