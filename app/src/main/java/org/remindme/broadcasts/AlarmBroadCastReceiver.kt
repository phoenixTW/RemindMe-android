package org.remindme.broadcasts

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.remindme.ui.DashboardActivity
import android.media.RingtoneManager
import android.media.Ringtone



class AlarmBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val contentTitle = intent.getStringExtra("TASK_TITLE")
        val notificationID = intent.getIntExtra("notificationId", 0)
        val dashBoardActivity = Intent(context, DashboardActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, dashBoardActivity, 0)
        val notification = Notification.Builder(context).apply {
            setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            setContentTitle(contentTitle)
            setContentText(intent.getCharSequenceExtra("reminder"))
            setWhen(System.currentTimeMillis())
            setPriority(Notification.PRIORITY_DEFAULT)
            setAutoCancel(true)
            setDefaults(Notification.DEFAULT_SOUND)
            setContentIntent(pendingIntent)
        }.build()
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationID, notification)

        val ringtoneNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone = RingtoneManager.getRingtone(context.applicationContext, ringtoneNotification)
        ringtone.play()
    }
}