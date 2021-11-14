package com.example.tasklist

import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.MotionEventCompat

class HighViewHolder(
    view: View,
    private val adapter: RecyclerAdapter
) : BaseViewHolder(view) {

    override fun bind(dataItem: Pair<Task, Int>) {
        val title = itemView.findViewById<TextView>(R.id.title)
        val titleEdit = itemView.findViewById<TextView>(R.id.title_edit)
        val content = itemView.findViewById<TextView>(R.id.content)
        val contentEdit = itemView.findViewById<TextView>(R.id.content_edit)
        val icEdit = itemView.findViewById<ImageView>(R.id.ic_edit)
        val icDelete = itemView.findViewById<ImageView>(R.id.ic_delete)
        val icSave = itemView.findViewById<ImageView>(R.id.ic_save)
        val icCancel = itemView.findViewById<ImageView>(R.id.ic_cancel)
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

        switchPriority.isChecked = when(dataItem.first.type){
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

        itemView.findViewById<ImageView>(R.id.ic_delete).setOnClickListener { removeItem() }
        itemView.findViewById<ImageView>(R.id.ic_arrow_down).setOnClickListener { moveDown() }
        itemView.findViewById<ImageView>(R.id.ic_arrow_up).setOnClickListener { moveUp() }
        itemView.findViewById<ImageView>(R.id.ic_edit).setOnClickListener { editTask() }
        itemView.findViewById<ImageView>(R.id.ic_save).setOnClickListener { saveTask(dataItem) }
        itemView.findViewById<ImageView>(R.id.ic_cancel).setOnClickListener { cancel() }

        itemView.findViewById<ImageView>(R.id.ic_drag).setOnTouchListener { _, event ->
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                adapter.dragListener.onStartDrag(this)
            }
            false
        }
    }

    private fun toggleText() {
        adapter.data[layoutPosition] = when (adapter.data[layoutPosition].second) {
            ITEM_STATE_CLOSE -> adapter.data[layoutPosition].first to ITEM_STATE_OPEN
            ITEM_STATE_OPEN -> adapter.data[layoutPosition].first to ITEM_STATE_CLOSE
            else -> adapter.data[layoutPosition].first to ITEM_STATE_CLOSE
        }
        adapter.notifyItemChanged(layoutPosition)
    }

    private fun removeItem() {
        adapter.data.removeAt(layoutPosition)
        adapter.notifyItemRemoved(layoutPosition)
    }

    private fun moveDown() {
        if (layoutPosition < adapter.data.size - 1) {

            val element = adapter.data.removeAt(layoutPosition)
            adapter.data.add(layoutPosition + 1, element)

            adapter.notifyItemMoved(layoutPosition, layoutPosition + 1)
        }
    }

    private fun moveUp() {
        if (layoutPosition > 1) {
            val element = adapter.data.removeAt(layoutPosition)
            adapter.data.add(layoutPosition - 1, element)
            adapter.notifyItemMoved(layoutPosition, layoutPosition - 1)
        }
    }

    private fun editTask() {
        adapter.data[layoutPosition] = when (adapter.data[layoutPosition].second) {
            ITEM_STATE_CLOSE -> adapter.data[layoutPosition].first to ITEM_STATE_EDIT
            ITEM_STATE_OPEN -> adapter.data[layoutPosition].first to ITEM_STATE_EDIT
            else -> adapter.data[layoutPosition].first to ITEM_STATE_EDIT
        }
        adapter.notifyItemChanged(layoutPosition)
    }

    private fun saveTask(dataItem: Pair<Task, Int>) {
        adapter.data[layoutPosition] = when (adapter.data[layoutPosition].second) {
            ITEM_STATE_EDIT -> {
                val titleEdit = itemView.findViewById<TextView>(R.id.title_edit)
                dataItem.first.title = titleEdit.text.toString()

                val contentEdit = itemView.findViewById<TextView>(R.id.content_edit)
                dataItem.first.content = contentEdit.text.toString()

                val switchPriority = itemView.findViewById<SwitchCompat>(R.id.switch_priority)
                dataItem.first.type = when (switchPriority.isChecked) {
                    true -> 1
                    else -> 0
                }
                adapter.data[layoutPosition].first to ITEM_STATE_CLOSE
            }
            else -> adapter.data[layoutPosition].first to ITEM_STATE_EDIT
        }
        adapter.notifyItemChanged(layoutPosition)
    }

    private fun cancel() {
        adapter.data[layoutPosition] = when (adapter.data[layoutPosition].second) {
            ITEM_STATE_EDIT -> adapter.data[layoutPosition].first to ITEM_STATE_CLOSE
            else -> adapter.data[layoutPosition].first to ITEM_STATE_EDIT
        }
        adapter.notifyItemChanged(layoutPosition)
    }


    override fun onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY)
    }

    override fun onItemClear() {
        itemView.setBackgroundColor(Color.WHITE)
    }
}