package org.remindme.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.remindme.ui.AlarmActivity


class AlarmBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val contentTitle = intent.getStringExtra("TASK_TITLE")
        val alarmIntent = Intent(context, AlarmActivity::class.java)
        alarmIntent.putExtra("DISPLAY_MESSAGE", contentTitle)
        alarmIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        context.applicationContext.startActivity(alarmIntent)
    }
}