package com.example.tasklist.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.MotionEventCompat
import com.example.tasklist.R
import com.example.tasklist.data.TYPE_HIGH_PRIORITY
import com.example.tasklist.data.TYPE_STANDARD_PRIORITY
import com.example.tasklist.data.Task

class HighViewHolder(
    view: View,
    adapter: RecyclerAdapter
) : BaseViewHolder(view, adapter) {

    @SuppressLint("ClickableViewAccessibility")
    override fun bind(dataItem: Pair<Task, Int>) {
        val title = itemView.findViewById<TextView>(R.id.title)
        val titleEdit = itemView.findViewById<TextView>(R.id.title_edit)
        val content = itemView.findViewById<TextView>(R.id.content)
        val contentEdit = itemView.findViewById<TextView>(R.id.content_edit)
        val icEdit = itemView.findViewById<ImageView>(R.id.ic_edit)
        val icDelete = itemView.findViewById<ImageView>(R.id.ic_delete)
        val icSave = itemView.findViewById<ImageView>(R.id.ic_save)
        val icCancel = itemView.findViewById<ImageView>(R.id.ic_cancel)
        val icArrowDown = itemView.findViewById<ImageView>(R.id.ic_arrow_down)
        val icArrowUp = itemView.findViewById<ImageView>(R.id.ic_arrow_up)
        val icDrag = itemView.findViewById<ImageView>(R.id.ic_drag)
        val switchPriority = itemView.findViewById<SwitchCompat>(R.id.switch_priority)

        title.text = dataItem.first.title
        titleEdit.text = dataItem.first.title
        content.text = dataItem.first.content
        contentEdit.text = dataItem.first.content

        content.visibility = when (dataItem.second) {
            ITEM_STATE_OPEN -> View.VISIBLE
            ITEM_STATE_CLOSE -> View.GONE
            else -> View.GONE
        }

        switchPriority.isChecked = when (dataItem.first.type) {
            0 -> false
            else -> true
        }

        when (dataItem.second) {
            ITEM_STATE_EDIT -> {
                itemView.setOnClickListener { }
                title.visibility = View.INVISIBLE
                titleEdit.visibility = View.VISIBLE
                content.visibility = View.GONE
                contentEdit.visibility = View.VISIBLE
                icEdit.visibility = View.INVISIBLE
                icDelete.visibility = View.INVISIBLE
                icSave.visibility = View.VISIBLE
                icCancel.visibility = View.VISIBLE
                switchPriority.visibility = View.VISIBLE
            }
            else -> {
                itemView.setOnClickListener { toggleText() }
                title.visibility = View.VISIBLE
                titleEdit.visibility = View.INVISIBLE
                contentEdit.visibility = View.GONE
                icEdit.visibility = View.VISIBLE
                icDelete.visibility = View.VISIBLE
                icSave.visibility = View.INVISIBLE
                icCancel.visibility = View.INVISIBLE
                switchPriority.visibility = View.GONE
            }
        }

        icDelete.setOnClickListener { removeItem() }
        icArrowDown.setOnClickListener { moveDown() }
        icArrowUp.setOnClickListener { moveUp() }
        icEdit.setOnClickListener { editTask() }
        icSave.setOnClickListener { saveTask(dataItem) }
        icCancel.setOnClickListener { cancel() }

        icDrag.setOnTouchListener { _, event ->
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                adapter.dragListener.onStartDrag(this)
            }
            false
        }
    }

    override fun onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY)
    }

    override fun onItemClear() {
        itemView.setBackgroundColor(Color.WHITE)
    }
}