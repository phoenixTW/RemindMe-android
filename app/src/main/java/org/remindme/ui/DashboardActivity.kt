package org.remindme.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.remindme.R
import org.remindme.model.handlers.ReminderHandler
import org.remindme.ui.adapters.ReminderItemsAdapter

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)
        showReminders()
    }

    private fun showReminders() {
        val reminderItemsAdapter = ReminderItemsAdapter(this, ReminderHandler(this).getAllReminders())
        val reminderList = findViewById<ListView>(R.id.reminder_list)
        reminderList.adapter = reminderItemsAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        showReminders()
    }

    fun goToNewReminder(view: View) {
        val newReminderActivityIntent = Intent(this, NewReminderActivity::class.java)
        startActivity(newReminderActivityIntent)
    }
}
