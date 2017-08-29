package org.remindme.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {
    val FORMAT = "dd-MM-yyyy"
    val FULL_FORMAT = "dd-MM-yyyy HH:mm:ss"
    val TIME_FORMAT = "HH:mm"

    private lateinit var dateFormat: SimpleDateFormat;

    fun getInDateFormat(date: String): Date {
        dateFormat = SimpleDateFormat(FORMAT, Locale.getDefault())
        return dateFormat.parse(date)
    }

    fun getInStringFormat(date: Date): String {
        dateFormat = SimpleDateFormat(FORMAT, Locale.getDefault())
        return dateFormat.format(date)
    }

    fun getFullStringFormat(date: Date): String {
        dateFormat = SimpleDateFormat(FULL_FORMAT, Locale.getDefault())
        return dateFormat.format(date)
    }

    fun getTime(time: String): Long {
        if (time.isEmpty()) {
            return 0
        }
        dateFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        return dateFormat.parse(time).time
    }
}