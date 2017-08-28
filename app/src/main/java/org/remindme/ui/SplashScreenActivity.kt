package org.remindme.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import org.remindme.R

class SplashScreenActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val delay: Long = 2000
        Handler().postDelayed({
            val dashBoardIntent = Intent(this, DashboardActivity::class.java)
            startActivity(dashBoardIntent)
            finish()
        }, delay)
    }
}
