package com.example.tasklist

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallBack(
    private var oldItems: List<Pair<Task, Int>>,
    private var newItems: List<Pair<Task, Int>>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition].first.id == newItems[oldItemPosition].first.id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldItems[oldItemPosition].first.title == newItems[oldItemPosition].first.title
                && oldItems[oldItemPosition].first.content == newItems[oldItemPosition].first.content
                && oldItems[oldItemPosition].first.isCompleted == newItems[oldItemPosition].first.isCompleted
                && oldItems[oldItemPosition].first.type == newItems[oldItemPosition].first.type
                && oldItems[oldItemPosition].first.dateCreation == newItems[oldItemPosition].first.dateCreation
                )
    }

//    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
//        val oldItem = oldItems[oldItemPosition]
//        val newItem = newItems[newItemPosition]
//
//        return Change(oldItem, newItem)
//    }
}