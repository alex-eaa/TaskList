package com.example.tasklist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.databinding.FragmentFirstBinding
import kotlin.random.Random
import kotlin.random.nextInt


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
            object : RecyclerAdapter.OnListItemClickListener {
                override fun onItemClick(data: Task) {
                    Toast.makeText(requireActivity(), data.title, Toast.LENGTH_SHORT).show()
                }
            },
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initView()

        binding.recyclerView.adapter = adapter
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        viewModel.taskLiveData
            .observe(viewLifecycleOwner, {
//                Log.d(TAG, "Load data: ${it.toString()}")
//                val newList = convertListTaskEntityToListPairsTask(it)
//                adapter.setItems(addToListPairsTaskHeader(newList))
            })

        viewModel.taskLiveData2
            .observe(viewLifecycleOwner, {
                Log.d(TAG, "taskLiveData2 Load data: ${it.toString()}")
                adapter.setItemsPair(it)
            })

        viewModel.getAllTask()
    }

    private fun initView() {
        binding.fab.setOnClickListener { view ->
            viewModel.insertTaskToDB(
                Task(
                    id = null,
                    title = "Task ${Random.nextInt(1, 1000)}",
                    content = "text text sdasdfsd asd sd asd ",
                    type = TYPE_STANDARD_PRIORITY,
                    isCompleted = false,
                    dateCreation = "2021-10"
                )
            )
        }

        binding.fabGetData.setOnClickListener { view ->
                    viewModel.saveAllTasksToDB(adapter.data)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_by_position -> {
                viewModel.getAllTask(ORDER_BY_POSITION)
                true
            }
            R.id.action_sort_by_position_desc -> {
                viewModel.getAllTask(ORDER_BY_POSITION_DESC)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}