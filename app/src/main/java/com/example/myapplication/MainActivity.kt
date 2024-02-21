package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var messageEditText: EditText
    private lateinit var selectDateTimeButton: Button
    private lateinit var scheduleButton: Button
    private lateinit var autoTurnOnDataCheckbox: CheckBox

    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0
    private var selectedHour = 0
    private var selectedMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageEditText = findViewById(R.id.messageEditText)
        selectDateTimeButton = findViewById(R.id.selectDateTimeButton)
        scheduleButton = findViewById(R.id.scheduleButton)
        autoTurnOnDataCheckbox = findViewById(R.id.autoTurnOnDataCheckbox)

        selectDateTimeButton.setOnClickListener {
            showDateTimePicker()
        }

        scheduleButton.setOnClickListener {
            scheduleMessage()
        }

        autoTurnOnDataCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (!isMobileDataEnabled()) {
                    requestTurnOnMobileData()
                }
            }
        }
    }

    private fun isMobileDataEnabled(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return info != null && info.isConnected
    }

    private fun requestTurnOnMobileData() {
        val intent = Intent(Settings.ACTION_DATA_USAGE_SETTINGS)
        startActivity(intent)
    }

    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                selectedYear = year
                selectedMonth = monthOfYear
                selectedDay = dayOfMonth
                showTimePicker()
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                selectedHour = hourOfDay
                selectedMinute = minute
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }

    private fun scheduleMessage() {
        val message = messageEditText.text.toString()
        val calendar = Calendar.getInstance()
        calendar.set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute)
        val scheduledTime = calendar.timeInMillis

        // Implement logic to schedule the message using AlarmManager or WorkManager
        // For simplicity, let's just print the scheduled time
        println("Message scheduled to be sent at: $scheduledTime")
    }
}
