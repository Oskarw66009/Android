package com.example.oj

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), OnDateSelectedListener {

    private lateinit var calendarView: MaterialCalendarView
    private lateinit var btnAddReminder: Button
    private lateinit var btnDeleteReminder: Button
    private lateinit var etReminderText: EditText
    private lateinit var tvSelectedDateTime: TextView
    private val calendar = Calendar.getInstance()
    private val eventDates = mutableSetOf<CalendarDay>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarView = findViewById(R.id.calendarView)
        btnAddReminder = findViewById(R.id.btnAddReminder)
        btnDeleteReminder = findViewById(R.id.btnDeleteReminder)
        etReminderText = findViewById(R.id.etReminderText)
        tvSelectedDateTime = findViewById(R.id.tvSelectedDateTime)

        calendarView.setOnDateChangedListener(this)
        calendarView.state().edit()
            .setMinimumDate(CalendarDay.from(2000, 0, 1))
            .setFirstDayOfWeek(Calendar.MONDAY)
            .setMinimumDate(CalendarDay.today())
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

// Wyświetlanie wcześniej zapisanych przypomnień
        val reminders = readReminders()
        for ((selectedDate, reminderData) in reminders) {
            val selectedDateTime = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(selectedDate)
            val calendarDay = CalendarDay.from(selectedDateTime)
            eventDates.add(calendarDay)
            calendarView.addDecorators(EventDecorator(this, eventDates))
        }
        btnAddReminder.setOnClickListener {
            val selectedDate = tvSelectedDateTime.text.toString().substringAfter("Wybrana data: ")
            val reminderText = etReminderText.text.toString()

            if (selectedDate.isNotEmpty() && reminderText.isNotEmpty()) {

                // Dodawanie zdarzenia w kalendarzu----------------------------------------------
                val selectedDateTime = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(selectedDate)
                val calendarDay = CalendarDay.from(selectedDateTime)
                eventDates.add(calendarDay)
                calendarView.addDecorators(EventDecorator(this, eventDates))

                // Zapisanie przypomnienia----------------------------------------------
                saveReminder(selectedDate, reminderText)

                showToast("Dodano przypomnienie na $selectedDate z treścią: $reminderText")
            } else {
                showToast("Wybierz datę i wprowadź treść przypomnienia")
            }
        }

        btnDeleteReminder.setOnClickListener {
            val selectedDate = tvSelectedDateTime.text.toString().substringAfter("Wybrana data: ")

            if (selectedDate.isNotEmpty()) {
                // Usuń zdarzenie z kalendarza----------------------------------------------
                val selectedDateTime = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(selectedDate)
                val calendarDay = CalendarDay.from(selectedDateTime)
                eventDates.remove(calendarDay)
                calendarView.addDecorators(EventDecorator(this, eventDates))

                // Usuń przypomnienie----------------------------------------------
                deleteReminder(selectedDate)

                showToast("Usunięto przypomnienie na $selectedDate")
            } else {
                showToast("Wybierz datę, aby usunąć przypomnienie")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    //zapisz----------------------------------------------
    private fun saveReminder(selectedDate: String, reminderText: String) {
        val sharedPreferences = getSharedPreferences("reminders", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(selectedDate, reminderText)
        editor.apply()
    }

    //usun----------------------------------------------
    private fun deleteReminder(selectedDate: String) {
        val sharedPreferences = getSharedPreferences("reminders", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(selectedDate)
        editor.apply()
    }

    private fun readReminders(): Map<String, Pair<String, Int>> {
        val sharedPreferences = getSharedPreferences("reminders", Context.MODE_PRIVATE)
        val reminders = mutableMapOf<String, Pair<String, Int>>()
        for ((selectedDate, reminderText) in sharedPreferences.all) {
            if (reminderText is String) {
                val earlyReminderMinutes = sharedPreferences.getInt("$selectedDate-early-reminder", 0)
                reminders[selectedDate] = Pair(reminderText, earlyReminderMinutes)
            }
        }
        return reminders
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        // Obsługa wybranej daty z kalendarza
        val selectedDate = date.date
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val selectedDateTime = format.format(selectedDate)
        tvSelectedDateTime.text = "Wybrana data: $selectedDateTime"

        // Sprawdź, czy dla wybranej daty istnieje przypomnienie
        val sharedPreferences = getSharedPreferences("reminders", Context.MODE_PRIVATE)
        val reminderText = sharedPreferences.getString(selectedDateTime, null)

        // Wyświetl treść przypomnienia, jeśli istnieje
        if (reminderText != null) {
            etReminderText.setText(reminderText)
        } else {
            etReminderText.setText("")
        }
    }

class EventDecorator(private val context: Context, private val dates: Set<CalendarDay>) : DayViewDecorator {

    private val calendar = Calendar.getInstance()
    private val drawable: Drawable = context.resources.getDrawable(R.drawable.ic_event)

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.setBackgroundDrawable(drawable)
    }
}}
