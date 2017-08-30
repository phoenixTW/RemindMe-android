package org.remindme.ui

import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import org.remindme.R


class AlarmActivity : AppCompatActivity() {

    private lateinit var ringtone: Ringtone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        startAlarm()
        val title = intent.getStringExtra("DISPLAY_MESSAGE")
        val textView = findViewById<TextView>(R.id.task_title)
        textView.text = title
        addGestureDetector()
    }

    private fun addGestureDetector() {
        val swipeText = findViewById<TextView>(R.id.swipe)
        swipeText.setOnClickListener {
            stopAlarm()
        }
    }

    private fun stopAlarm() {
        ringtone.stop()
        val dashBoardInent = Intent(this, DashboardActivity::class.java)
        startActivity(dashBoardInent)
    }

    private fun startAlarm() {
        val ringtoneNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(applicationContext, ringtoneNotification)
        ringtone.play()
    }
}
