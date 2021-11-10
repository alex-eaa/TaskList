package com.example.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    private var onListItemClickListener: OnListItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_STANDARD_PRIORITY = 0
        private const val TYPE_HIGH_PRIORITY = 1
        private const val TYPE_TITLE = 2
    }

    private var data: List<Task> = listOf<Task>()

    fun setData(data: List<Task>){
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_STANDARD_PRIORITY) {
            StandardViewHolder(inflater.inflate(R.layout.recycler_item_task, parent, false) as View)
        } else {
            HighViewHolder(
                inflater.inflate(
                    R.layout.recycler_item_task_high_priority,
                    parent,
                    false
                ) as View
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_STANDARD_PRIORITY){
            holder as StandardViewHolder
            holder.bind(data[position])
        }else{
            holder as HighViewHolder
            holder.bind(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position].isHighPriority) TYPE_HIGH_PRIORITY
        else TYPE_STANDARD_PRIORITY
    }

    inner class StandardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Task) {
            itemView.findViewById<AppCompatTextView>(R.id.title).text = data.title
            itemView.findViewById<AppCompatTextView>(R.id.content).text = data.content
        }
    }

    inner class HighViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Task) {
            itemView.findViewById<AppCompatTextView>(R.id.title).text = data.title
            itemView.findViewById<AppCompatTextView>(R.id.content).text = data.content
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(data: Task)
    }
}