package org.remindme.model

import java.text.SimpleDateFormat
import java.util.*

class Reminder(private val id: Int) : BaseModel {
    private lateinit var title: String
    private lateinit var date: Date

    constructor(title: String, reminderDate: Date) : this(0) {
        this.title = title
        this.date = reminderDate
    }

    override fun getID(): Int {
        return id
    }

    fun getTitle(): String {
        return title
    }

    fun getDate(): String {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        return dateFormatter.format(date)
    }
}