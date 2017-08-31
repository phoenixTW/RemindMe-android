package org.remindme.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.remindme.R
import org.remindme.model.Task
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
        val allReminders = ReminderHandler(this).getAllReminders()
        val reminderItemsAdapter = ReminderItemsAdapter(this, allReminders)
        val reminderList = findViewById<ListView>(R.id.reminder_list)
        reminderList.adapter = reminderItemsAdapter
        setClickEventOnTask(reminderList, allReminders)
    }

    private fun setClickEventOnTask(reminderList: ListView, allReminders: List<Task>) {
        reminderList.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val selectedTask = allReminders[position]
            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.setTitle(selectedTask.getTitle())
            alertDialog.setButton("Edit", DialogInterface.OnClickListener { dialog, which ->
                //                Edit functionallity come here
            })
            alertDialog.setButton2("Delete", DialogInterface.OnClickListener { dialog, which ->
                //                Delete functionallity comes here
            })
            alertDialog.show()
        }
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
