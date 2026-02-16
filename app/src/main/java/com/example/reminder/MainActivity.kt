package com.example.reminder

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

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
