package org.remindme.model

import org.remindme.model.handlers.ReminderHandler

interface TaskService {
    fun create(reminderHandler: ReminderHandler): Task
}