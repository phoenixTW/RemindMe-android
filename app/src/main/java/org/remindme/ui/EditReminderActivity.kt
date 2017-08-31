package org.remindme.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Switch
import org.remindme.R
import org.remindme.utils.DateFormatter

class EditReminderActivity : NewReminderActivity() {

    private lateinit var title: EditText
    private lateinit var date: EditText
    private lateinit var startTime: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.edit_reminder)
        val taskTitle = intent.getStringExtra("TASK_TITLE")
        val taskDate = intent.getStringExtra("TASK_DATE")
        val taskStartTime = intent.getLongExtra("TASK_START_TIME", 0)

        populateFields(taskTitle, taskDate, taskStartTime)
    }

    private fun populateFields(taskTitle: String?, taskDate: String?, taskStartTime: Long) {
        val zero = 0.toLong()
        title = findViewById(R.id.reminder_title)
        title.setText(taskTitle)

        date = findViewById(R.id.reminder_date)
        date.setText(taskDate)

        startTime = findViewById(R.id.reminder_start_time)
        val setAlarmSwitch: Switch = findViewById<Switch>(R.id.set_alarm)

        if (taskStartTime != zero) {
            setAlarmSwitch.isChecked = true
            setStartTimeView(View.VISIBLE)
            startTime.setText(DateFormatter().getFormattedTime(taskStartTime))
        } else
            setStartTimeView(View.INVISIBLE)
    }
}
