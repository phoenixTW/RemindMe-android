package org.remindme.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {
    val FORMAT = "dd-MM-yyyy"

    val dateFormat = SimpleDateFormat(FORMAT, Locale.getDefault())

    fun getInDateFormat(date: String): Date {
        return dateFormat.parse(date)
    }

    fun getInStringFormat(date: Date): String {
        return dateFormat.format(date)
    }
}