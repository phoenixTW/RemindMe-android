package org.remindme.model

import org.remindme.utils.DateFormatter
import java.text.SimpleDateFormat
import java.util.*

class Reminder(private val id: Int) : BaseModel {

    private lateinit var title: String
    private lateinit var date: Date
    private lateinit var createdAt: Date

    constructor(title: String, reminderDate: Date) : this(0) {
        this.title = title
        this.date = reminderDate
        this.createdAt = Date()
    }

    constructor(id: Int, title: String, reminderDate: Date) : this(id) {
        this.title = title
        this.date = reminderDate
        this.createdAt = Date()
    }

    constructor(id: Int, title: String, reminderDate: Date, createdAt: Date) : this(id) {
        this.title = title
        this.date = reminderDate
        this.createdAt = createdAt
    }

    override fun getID(): Int {
        return id
    }

    override fun getCreatedAt(): Date {
        return createdAt
    }

    fun getTitle(): String {
        return title
    }

    fun getDate(): Date {
        return date
    }
}