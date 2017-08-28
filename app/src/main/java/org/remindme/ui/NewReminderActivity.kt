package org.remindme.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import org.remindme.R
import org.remindme.model.Reminder
import org.remindme.model.handlers.ReminderHandler
import org.remindme.utils.RMDatePicker
import java.text.SimpleDateFormat
import java.util.*

class NewReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reminder)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val reminderDateField: EditText = findViewById(R.id.reminder_date)
        RMDatePicker(this, reminderDateField)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun createReminder(view: View) {
        val title = getReminderTitle()
        val date = getReminderDate()
        Log.d("Insert", "New Reminder titled " + title + " On " + date)

        val reminder = Reminder(title, date)
        val reminderHandler = ReminderHandler(this)
        reminderHandler.addReminder(reminder)

        finish()
    }

    private fun getReminderDate(): Date {
        val date = findViewById<EditText>(R.id.reminder_date)
        val FORMAT = "dd-MM-yyyy"
        val dateFormat = SimpleDateFormat(FORMAT)
        return dateFormat.parse(date.text.toString())
    }

    private fun getReminderTitle(): String {
        val title = findViewById<EditText>(R.id.reminder_title)
        return title.text.toString()
    }
}
