package com.example.reminder

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import java.util.Calendar
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    lateinit var editClassName: EditText
    lateinit var btnPickTime: Button
    lateinit var btnSave: Button
    lateinit var txtSaved: TextView

    var selectedHour = 0
    var selectedMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        editClassName = findViewById(R.id.editClassName)
        btnPickTime = findViewById(R.id.btnPickTime)
        btnSave = findViewById(R.id.btnSave)
        txtSaved = findViewById(R.id.txtSaved)

        val prefs = getSharedPreferences("schedule", MODE_PRIVATE)

        // Request notification permission (Android 13+)
        if (android.os.Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }

        // Time picker
        btnPickTime.setOnClickListener {

            val timePicker = TimePickerDialog(
                this,
                { _, hour, minute ->
                    selectedHour = hour
                    selectedMinute = minute
                    btnPickTime.text = String.format("Time: %02d:%02d", hour, minute)
                },
                selectedHour,
                selectedMinute,
                true
            )

            timePicker.show()
        }

        // Save button
        btnSave.setOnClickListener {

            val title = editClassName.text.toString()

            if (title.isBlank()) {
                editClassName.error = "Enter class name"
                return@setOnClickListener
            }

            prefs.edit()
                .putString("title", title)
                .putInt("hour", selectedHour)
                .putInt("minute", selectedMinute)
                .apply()

            txtSaved.text = String.format(
                "Saved: %s at %02d:%02d",
                title,
                selectedHour,
                selectedMinute
            )

            // Schedule alarm safely
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, ReminderReceiver::class.java)

            val pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
            calendar.set(Calendar.MINUTE, selectedMinute)
            calendar.set(Calendar.SECOND, 0)

            if (calendar.timeInMillis < System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }

            // SAFE alarm (no crash)
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }

        // Load saved data on start
        val savedTitle = prefs.getString("title", "")
        val savedHour = prefs.getInt("hour", 0)
        val savedMinute = prefs.getInt("minute", 0)

        if (savedTitle != "") {
            selectedHour = savedHour
            selectedMinute = savedMinute

            btnPickTime.text =
                String.format("Time: %02d:%02d", savedHour, savedMinute)

            txtSaved.text = String.format(
                "Saved: %s at %02d:%02d",
                savedTitle,
                savedHour,
                savedMinute
            )
        }
    }
}
