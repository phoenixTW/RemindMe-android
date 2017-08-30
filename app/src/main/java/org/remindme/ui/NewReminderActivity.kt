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
import android.widget.Toast
import org.remindme.R
import org.remindme.constants.TYPE
import org.remindme.model.Task
import org.remindme.model.handlers.ReminderHandler
import org.remindme.utils.DateFormatter
import org.remindme.utils.RMDatePicker
import org.remindme.utils.RMTimePicker
import java.util.*
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import org.remindme.broadcasts.AlarmBroadCastReceiver


class NewReminderActivity : AppCompatActivity() {
    private lateinit var title: EditText
    private lateinit var date: EditText
    private lateinit var startTime: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reminder)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = findViewById(R.id.reminder_title)

        date = findViewById(R.id.reminder_date)
        RMDatePicker(this, date)

        startTime = findViewById<EditText>(R.id.reminder_start_time)
        RMTimePicker(this, startTime)

        val setAlarmSwitch: Switch = findViewById<Switch>(R.id.set_alarm)
        setAlarmSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
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

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.save_reminder -> {
                    createTask()
                    return true
                }
            }
        }
        return false
    }

    fun createTask() {
        if (!isValid(listOf<EditText>(title, date))) {
            showToast("Title/Date can't be empty")
            return
        }

        val task: Task
        val title = getReminderTitle()
        val date = getReminderDate()
        val startTimeInMilliSeconds = getStartTime()

        Log.d("Insert", "New Reminder titled " + title + " On " + date)


        val isReminder = findViewById<Switch>(R.id.set_alarm).isChecked
        if (isReminder) {
            if (!isValid(listOf(startTime))) {
                showToast("Start time can't be empty")
                return
            }
            task = Task(title = title, date = date, startTime = startTimeInMilliSeconds, type = TYPE.REMINDER)
            setAlarm(task)
        } else
            task = Task(title = title, date = date, type = TYPE.TODO)

        val reminderHandler = ReminderHandler(this)
        reminderHandler.addReminder(task)

        finish()
    }

    private fun setAlarm(task: Task) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val startTime = Date(task.getStartTime())
        val receiverIntent = Intent(applicationContext, AlarmBroadCastReceiver::class.java)
        receiverIntent.putExtra("TASK_TITLE", task.getTitle())
        val pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                0,
                receiverIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
        )
        val timeInMillis = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, startTime.hours)
            set(Calendar.MINUTE, startTime.minutes)
            set(Calendar.SECOND, 0)
        }.timeInMillis
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun isValid(fields: List<EditText>): Boolean {
        val emptyFields = fields.filter { it.text.isEmpty() }
        return emptyFields.isEmpty()
    }

    private fun getStartTime(): Long {
        return DateFormatter().getTime(startTime.text.toString())
    }

    private fun getReminderDate(): Date {
        return DateFormatter().getInDateFormat(date.text.toString())
    }

    private fun getReminderTitle(): String {
        return title.text.toString()
    }
}
