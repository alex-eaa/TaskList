package com.example.tasklist.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.*
import com.example.tasklist.data.TYPE_STANDARD_PRIORITY
import com.example.tasklist.data.Task
import com.example.tasklist.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

    companion object {
        const val TAG = "qqq"
    }

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FirstFragmentViewModel by lazy {
        ViewModelProvider(this).get(FirstFragmentViewModel::class.java)
    }

    private val adapter: RecyclerAdapter by lazy {
        RecyclerAdapter(
            object : RecyclerAdapter.OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }
        )
    }

    private val itemTouchHelper: ItemTouchHelper by lazy {
        ItemTouchHelper(ItemTouchHelperCallback(adapter))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initView()

        binding.recyclerView.adapter = adapter
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        viewModel.taskPairsLiveData
            .observe(viewLifecycleOwner, {
                adapter.setItemsPair(addHeader(it))
            })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initView() {
        binding.fab.setOnClickListener { view ->
            var alreadyEmptyTask = false
            for (pair in adapter.data) {
                if (pair.first.position == BaseViewHolder.POSITION_FOR_NEW_TASK) {
                    alreadyEmptyTask = true
                    break
                }
            }

            if (!alreadyEmptyTask) {
                viewModel.insertNewTaskToDB(
                    Task(
                        id = null,
                        position = BaseViewHolder.POSITION_FOR_NEW_TASK,
                        title = "",
                        content = "",
                        type = TYPE_STANDARD_PRIORITY,
                        isCompleted = false,
                        dateCreation = "2021-10"
                    )
                )
            }
        }

//        binding.fabSaveData.setOnClickListener { view ->
//            viewModel.insertAllTasksInDB(delHeader(adapter.data))
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_by_position -> {
//                viewModel.setLiveData(ORDER_BY_POSITION)
                true
            }
            R.id.action_sort_by_position_desc -> {
//                viewModel.setLiveData(ORDER_BY_POSITION_DESC)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStop() {
        super.onStop()
        viewModel.insertAllTasksInDB(delHeader(adapter.data))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}