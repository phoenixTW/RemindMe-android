package org.remindme.ui

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Switch
import org.remindme.R
import org.remindme.constants.TYPE
import org.remindme.model.Task
import org.remindme.model.handlers.ReminderHandler
import org.remindme.utils.DateFormatter
import org.remindme.utils.RMDatePicker
import org.remindme.utils.RMTimePicker
import java.util.*

class NewReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reminder)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val reminderDateField: EditText = findViewById(R.id.reminder_date)
        RMDatePicker(this, reminderDateField)

        val reminderTimeField: EditText = findViewById<EditText>(R.id.reminder_start_time)
        RMTimePicker(this, reminderTimeField)

        val setAlarmSwitch: Switch = findViewById<Switch>(R.id.set_alarm)
        setAlarmSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)
                setStartTimeView(View.VISIBLE)
            else
                setStartTimeView(View.INVISIBLE)
        })
    }

    private fun setStartTimeView(visibility: Int) {
        findViewById<TextInputLayout>(R.id.reminder_layout_start_time).visibility = visibility
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_reminder, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            createTask()
            return true
        }
        return false
    }

    fun createTask() {
        var task: Task
        val title = getReminderTitle()
        val date = getReminderDate()
        val startTime = getStartTime()

        Log.d("Insert", "New Reminder titled " + title + " On " + date)

        val isReminder = findViewById<Switch>(R.id.set_alarm).isChecked
        if (isReminder)
            task = Task(title = title, date = date, startTime = startTime, type = TYPE.REMINDER)
        else
            task = Task(title = title, date = date, type = TYPE.TODO)

        val reminderHandler = ReminderHandler(this)
        reminderHandler.addReminder(task)

        finish()
    }

    private fun getStartTime(): Long {
        val timeField: EditText = findViewById<EditText>(R.id.reminder_start_time)
        return DateFormatter().getTime(timeField.text.toString())
    }

    private fun getReminderDate(): Date {
        val date = findViewById<EditText>(R.id.reminder_date)
        return DateFormatter().getInDateFormat(date.text.toString())
    }

    private fun getReminderTitle(): String {
        val title = findViewById<EditText>(R.id.reminder_title)
        return title.text.toString()
    }
}
