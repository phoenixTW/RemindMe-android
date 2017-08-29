package org.remindme.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import org.remindme.R
import org.remindme.model.Task
import org.remindme.utils.DateFormatter

class ReminderItemsAdapter(val context: Context, val remainders: List<Task>) : BaseAdapter() {


    override fun getItem(index: Int): Any {
        return remainders.get(index)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return remainders.size
    }

    override fun getView(index: Int, view: View?, viewGroup: ViewGroup?): View {
        var contentView: View? = view
        if (contentView == null) {
            contentView = LayoutInflater.from(context).inflate(R.layout.reminder_item, viewGroup, false)
        }

        val item: Task = getItem(index) as Task
        if (contentView != null) {
            val itemName = contentView.findViewById<TextView>(R.id.task_name)
            val date = contentView.findViewById<TextView>(R.id.date)
            itemName.text = item.getTitle()
            date.text = DateFormatter().getInStringFormat(item.getDate())
        }
        return contentView!!
    }
}