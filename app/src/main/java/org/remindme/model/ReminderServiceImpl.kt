package org.remindme.model

import org.remindme.constants.TYPE
import org.remindme.model.handlers.ReminderHandler

class ReminderServiceImpl(private val task: Task) : TaskService {
    override fun update(reminderHandler: ReminderHandler): Task {
        reminderHandler.updateReminder(task)
        return task
    }

    override fun create(reminderHandler: ReminderHandler): Task {
        val taskId = reminderHandler.addReminder(task).toInt()
        return Task(title = task.getTitle(), date = task.getDate(), startTime = task.getStartTime(), id = taskId, type = TYPE.REMINDER)
    }
}