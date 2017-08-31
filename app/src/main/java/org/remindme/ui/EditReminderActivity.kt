package org.remindme.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Switch
import org.remindme.R
import org.remindme.model.Task
import org.remindme.model.handlers.ReminderHandler
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.save_reminder -> {
                    updateTask()
                    return true
                }
            }
        }
        return false
    }

    private fun updateTask() {
        if (!isValid(listOf<EditText>(title, date))) {
            showToast("Title/Date can't be empty")
            return
        }

        val taskId = intent.getIntExtra("TASK_ID", 0)
        val task: Task
        val title = getReminderTitle()
        val date = getReminderDate()
        val startTimeInMilliSeconds = getStartTime()

        Log.d("Insert", "New Reminder titled $title On $date")

        if (isReminderTask()) {
            if (!isValid(listOf(startTime))) {
                showToast("Start time can't be empty")
                return
            }
            task = createReminderTask(title, date, startTimeInMilliSeconds, taskId)
            setAlarm(task)
        } else
            task = createToDoTask(title, date, taskId)

        val reminderHandler = ReminderHandler(this)
        reminderHandler.updateReminder(task)

        finish()
    }
}
