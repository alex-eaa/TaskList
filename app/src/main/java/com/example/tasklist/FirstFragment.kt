package com.example.tasklist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tasklist.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

    companion object{
        const val TAG = "FirstFragment"
    }

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FirstFragmentViewModel by lazy {
        ViewModelProvider(this).get(FirstFragmentViewModel::class.java)
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

        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            viewModel.saveTaskToDB(
                Task(15,1, "Task 1", "text text", false, false, "2021-10")
            )
            viewModel.saveTaskToDB(
                Task(16,2, "Task 2", "text2 text2 text2", false, false, "2021-10")
            )
        }

        viewModel.taskLiveData
            .observe(viewLifecycleOwner, {
                Log.d(TAG, "Load data: ${it.toString()}")

            })
        viewModel.getAllTask()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}