package com.example.tasklist

import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.MotionEventCompat

class StandardViewHolder(
    view: View,
    private var adapter: RecyclerAdapter
) : BaseViewHolder(view) {

    override fun bind(dataItem: Pair<Task, Int>) {
        itemView.findViewById<TextView>(R.id.title).text = dataItem.first.title

        val content = itemView.findViewById<TextView>(R.id.content)
        content.text = dataItem.first.content
        content.visibility = when (dataItem.second) {
            ITEM_STATE_OPEN -> View.VISIBLE
            ITEM_STATE_CLOSE -> View.GONE
            else -> View.GONE
        }

        itemView.setOnClickListener { toggleText() }
        itemView.findViewById<ImageView>(R.id.ic_delete).setOnClickListener { removeItem() }
        itemView.findViewById<ImageView>(R.id.ic_arrow_down).setOnClickListener { moveDown() }
        itemView.findViewById<ImageView>(R.id.ic_arrow_up).setOnClickListener { moveUp() }

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
            else -> adapter.data[layoutPosition].first to ITEM_STATE_EDIT
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

    override fun onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY)
    }

    override fun onItemClear() {
        itemView.setBackgroundColor(Color.WHITE)
    }
}