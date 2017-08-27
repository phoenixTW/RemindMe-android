package org.remindme.utils

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

class RMDatePicker(val mCalendar: Calendar? = Calendar.getInstance())
    : View.OnClickListener, DatePickerDialog.OnDateSetListener {

    lateinit var context: Context
    lateinit var editText: EditText
    val FORMAT: String = "dd-MM-yyyy"

    constructor(context: Context, editText: EditText) : this() {
        this.context = context
        this.editText = editText
        this.editText.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        if (mCalendar != null) {
            mCalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
            DatePickerDialog(context,
                    this,
                    mCalendar.get(Calendar.YEAR),
                    mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, monthOfyear: Int, dayOfMonth: Int) {
        val dateFormat = SimpleDateFormat(FORMAT, Locale.getDefault())
        if (mCalendar != null) {
            mCalendar.set(Calendar.YEAR, year)
            mCalendar.set(Calendar.MONTH, monthOfyear)
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            editText.setText(dateFormat.format(mCalendar.time))
        }
    }

}