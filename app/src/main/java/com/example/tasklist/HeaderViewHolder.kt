package com.example.tasklist

import android.view.View
import androidx.appcompat.widget.AppCompatTextView

class HeaderViewHolder(
    view: View
) : BaseViewHolder(view) {
    override fun bind(dataItem: Pair<Task, Int>) {
        itemView.findViewById<AppCompatTextView>(R.id.title).text = dataItem.first.title
    }

    override fun onItemSelected() {}
    override fun onItemClear() {}
}