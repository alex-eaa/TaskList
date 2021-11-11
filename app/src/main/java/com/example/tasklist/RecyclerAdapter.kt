package com.example.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

const val ITEM_STATE_CLOSE = 0
const val ITEM_STATE_OPEN = 1
const val ITEM_STATE_EDIT = 2

class RecyclerAdapter(
    private var onListItemClickListener: OnListItemClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {

    private var data: MutableList<Pair<Task, Int>> = mutableListOf()

    fun setItems(newItems: List<Pair<Task, Int>>) {
        val result = DiffUtil.calculateDiff(DiffUtilCallBack(data, newItems))
        result.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_STANDARD_PRIORITY -> {
                StandardViewHolder(
                    inflater.inflate(
                        R.layout.recycler_item_task,
                        parent,
                        false
                    ) as View
                )
            }
            TYPE_HIGH_PRIORITY -> {
                HighViewHolder(
                    inflater.inflate(
                        R.layout.recycler_item_task_high_priority,
                        parent,
                        false
                    ) as View
                )
            }
            else -> {
                HeaderViewHolder(
                    inflater.inflate(
                        R.layout.recycler_item_header,
                        parent,
                        false
                    ) as View
                )
            }
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].first.type
    }

    inner class StandardViewHolder(view: View) : BaseViewHolder(view) {
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
        }

        private fun toggleText() {
            data[layoutPosition] = when (data[layoutPosition].second) {
                ITEM_STATE_CLOSE -> data[layoutPosition].first to ITEM_STATE_OPEN
                ITEM_STATE_OPEN -> data[layoutPosition].first to ITEM_STATE_CLOSE
                else -> data[layoutPosition].first to ITEM_STATE_EDIT
            }
            notifyItemChanged(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveDown() {
            if (layoutPosition < data.size - 1) {
                val element = data.removeAt(layoutPosition)
                data.add(layoutPosition + 1, element)
                notifyItemMoved(layoutPosition, layoutPosition + 1)
            }
        }

        private fun moveUp() {
            if (layoutPosition > 1) {
                val element = data.removeAt(layoutPosition)
                data.add(layoutPosition - 1, element)
                notifyItemMoved(layoutPosition, layoutPosition - 1)
            }
        }

    }


    inner class HighViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(dataItem: Pair<Task, Int>) {
            itemView.findViewById<AppCompatTextView>(R.id.title).text = dataItem.first.title
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
        }

        private fun toggleText() {
            data[layoutPosition] = when (data[layoutPosition].second) {
                ITEM_STATE_CLOSE -> data[layoutPosition].first to ITEM_STATE_OPEN
                ITEM_STATE_OPEN -> data[layoutPosition].first to ITEM_STATE_CLOSE
                else -> data[layoutPosition].first to ITEM_STATE_EDIT
            }
            notifyItemChanged(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveDown() {
            if (layoutPosition < data.size - 1) {
                val element = data.removeAt(layoutPosition)
                data.add(layoutPosition + 1, element)

                notifyItemMoved(layoutPosition, layoutPosition + 1)
            }
        }

        private fun moveUp() {
            if (layoutPosition > 1) {
                val element = data.removeAt(layoutPosition)
                data.add(layoutPosition - 1, element)
                notifyItemMoved(layoutPosition, layoutPosition - 1)
            }
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(dataItem: Pair<Task, Int>) {
            itemView.findViewById<AppCompatTextView>(R.id.title).text = dataItem.first.title
        }
    }


    interface OnListItemClickListener {
        fun onItemClick(data: Task)
    }
}