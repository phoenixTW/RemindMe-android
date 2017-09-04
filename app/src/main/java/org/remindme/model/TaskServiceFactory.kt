package org.remindme.model

import org.remindme.constants.TYPE
import org.remindme.utils.DateFormatter
import java.util.*

class TaskServiceFactory(private val title: String,
                         private val date: String,
                         private val startTime: String,
                         private val id: Int = 0) {


    fun getService(): TaskService {
        val task: Task
        if (startTime.isEmpty()) {
            task = Task(title = title, date = getReminderDate(), type = TYPE.TODO, id = id)
            return ToDoServiceImpl(task)
        }
        task = Task(title = title, date = getReminderDate(), startTime = getStartTime(), type = TYPE.REMINDER, id = id)
        return ReminderServiceImpl(task)
    }

    private fun getStartTime(): Long {
        return DateFormatter().getTime(startTime)
    }

    private fun getReminderDate(): Date {
        return DateFormatter().getInDateFormat(date)
    }
}