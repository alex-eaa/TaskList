package com.example.tasklist.ui

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.example.tasklist.R
import com.example.tasklist.data.Task

class HeaderViewHolder(
    view: View,
    adapter: RecyclerAdapter
) : BaseViewHolder(view, adapter) {
    override fun bind(dataItem: Pair<Task, Int>) {
        itemView.findViewById<AppCompatTextView>(R.id.title).text = dataItem.first.title
    }

    override fun onItemSelected() {}
    override fun onItemClear() {}
}