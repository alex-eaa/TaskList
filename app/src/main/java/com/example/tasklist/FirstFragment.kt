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

        binding.recyclerView.adapter = adapter

        viewModel.saveTaskToDB(
            Task(null, 1, "Task 1", "text text sdasdfsd asd sd asd ", false, false, "2021-10")
        )
        viewModel.saveTaskToDB(
            Task(null, 2, "Task 2", "text2 text2 text2 asd af dad a fd ", false, false, "2021-10")
        )
        viewModel.saveTaskToDB(
            Task(null, 3, "Задача 3", "text2 text2 text2 asd af dad a fd  ывоарплфыва ыфвларопфлоырапфыл валоыврп аылфв", true, false, "2021-10")
        )

        viewModel.taskLiveData
            .observe(viewLifecycleOwner, {
                Log.d(TAG, "Load data: ${it.toString()}")
                adapter.setData(it.map { taskEntity ->
                    convertEntityToTask(taskEntity)
                })
                adapter.notifyDataSetChanged()
            })
        viewModel.getAllTask()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}