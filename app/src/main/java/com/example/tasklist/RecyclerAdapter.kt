package com.example.tasklist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(
    private var onListItemClickListener: OnListItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: MutableList<Task> = mutableListOf()

    fun setData(data: List<Task>) {
        this.data = data.toMutableList()
        this.data.add(0, Task(type = TYPE_HEADER, title = "МОИ ЗАДАЧИ"))
        Log.d("APP", this.data.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_STANDARD_PRIORITY -> {
                holder as StandardViewHolder
                holder.bind(data[position])
            }
            TYPE_HIGH_PRIORITY -> {
                holder as HighViewHolder
                holder.bind(data[position])
            }
            else -> {
                holder as HeaderViewHolder
                holder.bind(data[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type
    }

    inner class StandardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var visible: Boolean = false
        fun bind(data: Task) {
            itemView.findViewById<TextView>(R.id.title).text = data.title
            val content = itemView.findViewById<TextView>(R.id.content)
            content.text = data.content

            itemView.setOnClickListener {
                visible = !visible
                if (visible) content.visibility = View.VISIBLE
                else content.visibility = View.GONE
            }
        }
    }

    inner class HighViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var visible: Boolean = false
        fun bind(data: Task) {
            itemView.findViewById<AppCompatTextView>(R.id.title).text = data.title
            val content = itemView.findViewById<TextView>(R.id.content)
            content.text = data.content
            itemView.setOnClickListener {
//                onListItemClickListener.onItemClick(data)
                visible = !visible
                if (visible) content.visibility = View.VISIBLE
                else content.visibility = View.GONE
            }
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Task) {
            itemView.findViewById<AppCompatTextView>(R.id.title).text = data.title
        }
    }


    interface OnListItemClickListener {
        fun onItemClick(data: Task)
    }
}