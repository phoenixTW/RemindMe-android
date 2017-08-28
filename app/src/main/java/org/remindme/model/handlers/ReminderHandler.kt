package org.remindme.model.handlers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.remindme.model.Reminder

class ReminderHandler(private val context: Context,
                      private val DATABASE_NAME: String = "remind_me",
                      private val DATABASE_VERSION: Int = 1)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val TABLE = "reminder"
    private val PRIMARY_KEY = "id"
    private val KEY_TITLE = "title"
    private val KEY_DATE = "date"


    override fun onCreate(database: SQLiteDatabase?) {
        val CREATE_REMINDER_TABLE: String = "CREATE TABLE " +
                TABLE + "(" +
                PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_TITLE + " TEXT, " +
                KEY_DATE + " DATE)"
        if (database != null) {
            database.execSQL(CREATE_REMINDER_TABLE)
        }
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (database != null) {
            database.execSQL("DROP TABLE IF EXISTS" + TABLE)
            onCreate(database)
        }
    }

    fun addReminder(reminder: Reminder) {
        val database: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()

        values.put(KEY_TITLE, reminder.getTitle())
        values.put(KEY_DATE, reminder.getDate())

        database.insert(TABLE, null, values)
        database.close()
    }

    fun getAllReminders(): List<Reminder> {
        val reminders: List<Reminder> = ArrayList<Reminder>()
        return reminders;
    }

}