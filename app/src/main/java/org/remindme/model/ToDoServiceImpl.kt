package org.remindme.model

import org.remindme.model.handlers.ReminderHandler

class ToDoServiceImpl(private val task: Task) : TaskService {
    override fun create(reminderHandler: ReminderHandler): Task {
        reminderHandler.addReminder(task)
        return task
    }
}