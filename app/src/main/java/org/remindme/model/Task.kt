package org.remindme.model

import org.remindme.constants.TYPE
import java.util.*

class Task(private val id: Int = 0,
           private val title: String,
           private val date: Date,
           private val startTime: Long = 0,
           private val type: TYPE,
           private val createdAt: Date = Date()) : BaseModel {


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

    fun getStartTime(): Long {
        return startTime
    }

    fun getType(): TYPE {
        return type
    }
}