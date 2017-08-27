package org.remindme.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import org.remindme.R
import org.remindme.utils.RMDatePicker

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
        finish()
    }
}
