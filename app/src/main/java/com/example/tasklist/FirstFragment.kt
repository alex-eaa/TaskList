package com.example.tasklist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tasklist.databinding.FragmentFirstBinding
import kotlin.random.Random
import kotlin.random.nextInt


class FirstFragment : Fragment() {

    companion object {
        const val TAG = "FirstFragment"
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
            }
        )
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

        initView()

        binding.recyclerView.adapter = adapter

        viewModel.taskLiveData
            .observe(viewLifecycleOwner, {
                Log.d(TAG, "Load data: ${it.toString()}")
                val newList = convertListTaskEntityToListPairsTask(it)
                adapter.setItems(addToListPairsTaskHeader(newList))
            })
        viewModel.getAllTask()
    }

    private fun initView() {
        binding.fab.setOnClickListener { view ->
            viewModel.saveTaskToDB(
                Task(
                    null,
                    Random.nextInt(1, 100),
                    "Task ${Random.nextInt(1,1000)}",
                    "text text sdasdfsd asd sd asd ",
                    TYPE_STANDARD_PRIORITY,
                    false,
                    "2021-10"
                )
            )
            viewModel.saveTaskToDB(
                Task(
                    null,
                    Random.nextInt(1, 100),
                    "Выполнить ${Random.nextInt(1,1000)}",
                    "text2 text2 text2 asd af dad a fd ",
                    TYPE_STANDARD_PRIORITY,
                    Random.nextBoolean(),
                    "2021-10"
                )
            )
            viewModel.saveTaskToDB(
                Task(
                    null,
                    Random.nextInt(1,100),
                    "Task ${Random.nextInt(1,1000)}",
                    "text2 text2 text2 asd af dad a fd  ывоарплфыва ыфвларопфлоырапфыл валоыврп аылфв",
                    TYPE_HIGH_PRIORITY,
                    Random.nextBoolean(),
                    "2021-10"
                )
            )
        }

        binding.fabGetData.setOnClickListener { view ->
            viewModel.getAllTask()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}