package org.remindme.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import org.remindme.broadcasts.AlarmBroadCastReceiver
import org.remindme.constants.TYPE
import org.remindme.model.*
import org.remindme.model.handlers.ReminderHandler
import org.remindme.utils.DateFormatter
import org.remindme.utils.RMDatePicker
import org.remindme.utils.RMTimePicker
import java.util.*


open class NewReminderActivity : AppCompatActivity() {
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

    internal fun setStartTimeView(visibility: Int) {
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
                    saveTask()
                    return true
                }
            }
        }
        return false
    }

    private fun saveTask() {
        val title = title.text.toString()
        val date = date.text.toString()
        val time = startTime.text.toString()

        Log.d("Insert", "New Reminder titled $title On $date")

        try {
            Validator().isValid(title, date, time, getSwitchState())
            createTask(title, date, time)
            finish()
        } catch (error: Exception) {
            showToast(error.message!!)
        }
    }

    private fun createTask(title: String, date: String, time: String) {
        val reminderHandler = ReminderHandler(this)
        val taskServiceFactory = TaskServiceFactory(title, date, time)
        val taskService = taskServiceFactory.getService()
        val task = taskService.create(reminderHandler)
        if (!time.isEmpty())
            setAlarm(task)
    }

    internal fun createToDoTask(title: String, date: Date, id: Int = 0) =
            Task(id = id, title = title, date = date, type = TYPE.TODO)

    internal fun createReminderTask(title: String, date: Date, startTimeInMilliSeconds: Long, id: Int = 0) =
            Task(id = id, title = title, date = date, startTime = startTimeInMilliSeconds, type = TYPE.REMINDER)

    internal fun getSwitchState() = findViewById<Switch>(R.id.set_alarm).isChecked

    internal fun setAlarm(task: Task) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val dateFormatter = DateFormatter()
        val date = dateFormatter.getInStringFormat(task.getDate())
        val startTime = dateFormatter.getFormattedTime(task.getStartTime())
        val receiverIntent = Intent(applicationContext, AlarmBroadCastReceiver::class.java)
        receiverIntent.putExtra("TASK_TITLE", task.getTitle())
        val pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                task.getID(),
                receiverIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
        )
        val timeInMillis = dateFormatter.getDateTimeInMilliSec(date, startTime)
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
    }

    internal fun cancelAlarm(taskId: Int) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val receiverIntent = Intent(this, AlarmBroadCastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, taskId, receiverIntent, 0)
        alarmManager.cancel(pendingIntent)
    }

    internal fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    internal fun isValid(fields: List<EditText>): Boolean {
        val emptyFields = fields.filter { it.text.isEmpty() }
        return emptyFields.isEmpty()
    }

    internal fun getStartTime(): Long {
        return DateFormatter().getTime(startTime.text.toString())
    }

    internal fun getReminderDate(): Date {
        return DateFormatter().getInDateFormat(date.text.toString())
    }

    internal fun getReminderTitle(): String {
        return title.text.toString()
    }
}
