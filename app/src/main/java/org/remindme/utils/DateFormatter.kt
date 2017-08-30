package org.remindme.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {
    val DATE_FORMAT = "dd-MM-yyyy"
    val FULL_FORMAT = "dd-MM-yyyy HH:mm:ss"
    val DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm"
    val TIME_FORMAT = "HH:mm"

    private lateinit var dateFormat: SimpleDateFormat;

    fun getInDateFormat(date: String): Date {
        dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return dateFormat.parse(date)
    }

    fun getInStringFormat(date: Date): String {
        dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
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

    fun getFormattedTime(milliSecond: Long): String {
        val date: Date = Date(milliSecond)
        return date.hours.toString() + ":" + date.minutes
    }

    fun getDateTimeInMilliSec(date: String, time: String): Long {
        dateFormat = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
        return dateFormat.parse(date + " " + time).time
    }
}