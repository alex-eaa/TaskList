package com.example.tasklist.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.data.Task

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    ItemTouchHelperViewHolder {
    abstract fun bind(dataItem: Pair<Task, Int>)

    companion object {
        const val POSITION_FOR_NEW_TASK = 1000000
    }
}