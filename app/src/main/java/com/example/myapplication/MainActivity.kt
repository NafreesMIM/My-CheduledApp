package com.example.myapplication
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var editTextMessage: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var editTextDateTime: EditText
    private lateinit var checkBoxMobileData: CheckBox
    private lateinit var buttonSendNow: Button
    private lateinit var buttonSchedule: Button
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        editTextMessage = findViewById(R.id.editTextMessage)
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber)
        editTextDateTime = findViewById(R.id.editTextDateTime)
        checkBoxMobileData = findViewById(R.id.checkBoxMobileData)
        buttonSendNow = findViewById(R.id.buttonSendNow)
        buttonSchedule = findViewById(R.id.buttonSchedule)

        // Set click listeners
        editTextDateTime.setOnClickListener {
            showDateTimePicker()
        }

        buttonSendNow.setOnClickListener {
            sendMessage(editTextMessage.text.toString(), editTextPhoneNumber.text.toString())
        }

        buttonSchedule.setOnClickListener {
            scheduleMessage(editTextMessage.text.toString(), editTextPhoneNumber.text.toString())
        }
    }

    private fun showDateTimePicker() {
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                showTimePicker()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.minDate = System.currentTimeMillis()
        datePicker.show()
    }

    private fun showTimePicker() {
        val timePicker = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                updateDateTime()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        timePicker.show()
    }

    private fun updateDateTime() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        editTextDateTime.setText(dateFormat.format(calendar.time))
    }

    private fun sendMessage(message: String, phoneNumber: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, message)
        sendIntent.type = "text/plain"
        sendIntent.setPackage("com.whatsapp")
        startActivity(sendIntent)
    }

    private fun scheduleMessage(message: String, phoneNumber: String) {
        // Implement logic to schedule the message
        Toast.makeText(this, "Message scheduled for later", Toast.LENGTH_SHORT).show()
    }
}
