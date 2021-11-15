package com.example.tasklist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

const val ITEM_STATE_CLOSE = 0
const val ITEM_STATE_OPEN = 1
const val ITEM_STATE_EDIT = 2

class RecyclerAdapter(
    val dragListener: OnStartDragListener,
) : RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {
//Для выполнения сравнения в фоновом потоке использовать ListAdapter вместо RecyclerView.Adapter

    var data: MutableList<Pair<Task, Int>> = mutableListOf()

    fun setItemsPair(newItems: List<Pair<Task, Int>>) {
        val result = DiffUtil.calculateDiff(DiffUtilCallBack(data, newItems))
        result.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newItems)
//        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_STANDARD_PRIORITY -> {
                StandardViewHolder(
                    inflater.inflate(
                        R.layout.recycler_item_task,
                        parent, false
                    ) as View,
                    this
                )
            }
            TYPE_HIGH_PRIORITY -> {
                HighViewHolder(
                    inflater.inflate(
                        R.layout.recycler_item_task_high_priority,
                        parent, false
                    ) as View,
                    this
                )
            }
            else -> {
                HeaderViewHolder(
                    inflater.inflate(
                        R.layout.recycler_item_header,
                        parent, false
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


    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (toPosition != 0) {
            val pos = data[fromPosition].first.position
            data[fromPosition].first.position = data[toPosition].first.position
            data[toPosition].first.position = pos

            val element = data.removeAt(fromPosition)
            if (toPosition > fromPosition) data.add((toPosition), element)
            else data.add((toPosition), element)

            notifyItemMoved(fromPosition, toPosition)
        }
    }

    override fun onItemDismiss(position: Int) {
//        firstFragmentViewModel.deleteTaskFromDB(data[position].first)
        data.removeAt(position)
        notifyItemRemoved(position)
    }


    interface OnListItemClickListener {
        fun onItemClick(data: Task)
    }

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }
}