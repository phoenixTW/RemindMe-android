package org.remindme.utils

import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TimePicker
import java.util.*

class RMTimePicker(val mCalendar: Calendar? = Calendar.getInstance()) : View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var context: Context
    private lateinit var editText: EditText

    constructor(context: Context, editText: EditText) : this() {
        this.context = context
        this.editText = editText
        this.editText.setOnClickListener(this)
    }
    override fun onClick(view: View?) {
        val hour: Int = mCalendar!!.get(Calendar.HOUR_OF_DAY)
        val minute: Int = mCalendar.get(Calendar.MINUTE)
        TimePickerDialog(context, this, hour, minute, true).show()
    }

    override fun onTimeSet(timePicker: TimePicker?, hourOfDay: Int, minuteOfDay: Int) {
        val time: String = hourOfDay.toString() + ":" + minuteOfDay.toString()
        editText.setText(time)
    }
}