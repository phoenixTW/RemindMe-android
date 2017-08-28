package org.remindme.model.handlers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.remindme.model.Reminder
import org.remindme.utils.DateFormatter
import java.util.*

class ReminderHandler(private val context: Context,
                      private val DATABASE_NAME: String = "remind_me",
                      private val DATABASE_VERSION: Int = 1)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val TABLE = "reminder"
    private val PRIMARY_KEY = "id"
    private val KEY_TITLE = "title"
    private val KEY_DATE = "date"
    private val KEY_CREATED_AT = "created_at"


    override fun onCreate(database: SQLiteDatabase?) {
        val CREATE_REMINDER_TABLE: String = "CREATE TABLE " +
                TABLE + "(" +
                PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_TITLE + " TEXT, " +
                KEY_DATE + " DATE, " +
                KEY_CREATED_AT + " DATE NOT NULL)";
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
        values.put(KEY_DATE, DateFormatter().getInStringFormat(reminder.getDate()))
        values.put(KEY_CREATED_AT, DateFormatter().getFullStringFormat(reminder.getCreatedAt()))

        database.insert(TABLE, null, values)
        database.close()
    }

    fun getAllReminders(): List<Reminder> {
        val reminders: MutableList<Reminder> = ArrayList<Reminder>()
        val GET_ALL_REMINDER_QUERY: String = "SELECT * FROM " +
                TABLE + " ORDER BY " + KEY_CREATED_AT + " DESC";
        val database = this.readableDatabase
        val cursor: Cursor = database.rawQuery(GET_ALL_REMINDER_QUERY, null)

        if (cursor.moveToFirst()) {
            do {
                val id: Int = cursor.getString(0).toInt()
                val title: String = cursor.getString(1)
                val date: Date = DateFormatter().getInDateFormat(cursor.getString(2))
                val createdAt: Date = DateFormatter().getInDateFormat(cursor.getString(3))
                val reminder: Reminder = Reminder(id, title, date, createdAt)
                reminders.add(reminder)
            } while (cursor.moveToNext())
        }

        return reminders;
    }

}