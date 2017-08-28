package org.remindme.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {
    val FORMAT = "dd-MM-yyyy"
    val FULL_FORMAT = "dd-MM-yyyy HH:mm:ss"

    val dateFormat = SimpleDateFormat(FORMAT, Locale.getDefault())
    val dateFormatFull = SimpleDateFormat(FULL_FORMAT, Locale.getDefault())

    fun getInDateFormat(date: String): Date {
        return dateFormat.parse(date)
    }

    fun getInStringFormat(date: Date): String {
        return dateFormat.format(date)
    }

    fun getFullStringFormat(date: Date): String {
        return dateFormatFull.format(date)
    }
}