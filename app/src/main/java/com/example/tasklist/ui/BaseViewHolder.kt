package com.example.tasklist.ui

import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.R
import com.example.tasklist.data.TYPE_HIGH_PRIORITY
import com.example.tasklist.data.TYPE_STANDARD_PRIORITY
import com.example.tasklist.data.Task
import com.example.tasklist.delHeader

abstract class BaseViewHolder(
    itemView: View,
    val adapter: RecyclerAdapter
) :
    RecyclerView.ViewHolder(itemView),
    ItemTouchHelperViewHolder {

    companion object {
        const val POSITION_FOR_NEW_TASK = 1000000
    }

    abstract fun bind(dataItem: Pair<Task, Int>)

    fun moveUp() {
        adapter.data.let {
            if (layoutPosition > 1) {
                val positionTemp1 = it[layoutPosition].first.position
                val positionTemp2 = it[layoutPosition - 1].first.position
                it[layoutPosition].first.position = positionTemp2
                it[layoutPosition - 1].first.position = positionTemp1

                val element = it.removeAt(layoutPosition)
                it.add(layoutPosition - 1, element)
            }
        }
        adapter.notifyItemMoved(layoutPosition, layoutPosition - 1)
    }

    fun moveDown() {
        adapter.data.let {
            if (layoutPosition < it.size - 1) {
                val positionTemp1 = it[layoutPosition].first.position
                val positionTemp2 = it[layoutPosition + 1].first.position
                it[layoutPosition].first.position = positionTemp2
                it[layoutPosition + 1].first.position = positionTemp1

                val element = it.removeAt(layoutPosition)
                it.add(layoutPosition + 1, element)
            }
            adapter.notifyItemMoved(layoutPosition, layoutPosition + 1)
        }
    }

    fun toggleText() {
        adapter.data[layoutPosition] = when (adapter.data[layoutPosition].second) {
            ITEM_STATE_CLOSE -> adapter.data[layoutPosition].first to ITEM_STATE_OPEN
            else -> adapter.data[layoutPosition].first to ITEM_STATE_CLOSE
        }
        adapter.notifyItemChanged(layoutPosition)
    }

    fun removeItem() {
        adapter.data.removeAt(layoutPosition)
        adapter.firstFragmentViewModel.insertAllTasksInDB(delHeader(adapter.data))
        adapter.notifyItemRemoved(layoutPosition)
    }

    fun cancel() {
        adapter.data[layoutPosition] = adapter.data[layoutPosition].first to ITEM_STATE_OPEN
        adapter.notifyItemChanged(layoutPosition)
    }

    fun editTask() {
        adapter.data[layoutPosition] = adapter.data[layoutPosition].first to ITEM_STATE_EDIT
        adapter.notifyItemChanged(layoutPosition)
    }

    fun saveTask(dataItem: Pair<Task, Int>) {
        getDataFromFields(dataItem.first)
        adapter.data[layoutPosition] = adapter.data[layoutPosition].first to ITEM_STATE_OPEN

        if (dataItem.first.position == POSITION_FOR_NEW_TASK) {
            dataItem.first.position = getMaxPosition() + 1
        }

        adapter.firstFragmentViewModel.insertNewTaskToDB(dataItem.first)
        adapter.notifyItemChanged(layoutPosition)
    }

    private fun getDataFromFields(task: Task) {
        val titleEdit = itemView.findViewById<TextView>(R.id.title_edit)
        task.title = titleEdit.text.toString()

        val contentEdit = itemView.findViewById<TextView>(R.id.content_edit)
        task.content = contentEdit.text.toString()

        val switchPriority = itemView.findViewById<SwitchCompat>(R.id.switch_priority)
        task.type = when (switchPriority.isChecked) {
            true -> TYPE_HIGH_PRIORITY
            else -> TYPE_STANDARD_PRIORITY
        }
    }

    private fun getMaxPosition(): Int {
        var maxPosition = 0
        adapter.data.forEach {
            if (it.first.position in (maxPosition + 1) until POSITION_FOR_NEW_TASK) {
                maxPosition = it.first.position
            }
        }
        return maxPosition
    }
}