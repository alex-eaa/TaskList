package com.example.tasklist

import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.*
import com.example.tasklist.App.Companion.getTaskDao
import com.example.tasklist.data.Task
import com.example.tasklist.data.TaskEntity
import com.example.tasklist.repository.LocalRepository
import com.example.tasklist.repository.LocalRepositoryImpl
import com.example.tasklist.repository.ORDER_BY_POSITION_DESC

class FirstFragmentViewModel : ViewModel() {

    private val handlerThread = HandlerThread("HandlerThread")

    init {
        handlerThread.start()
    }

    private val localRepository: LocalRepository = LocalRepositoryImpl(getTaskDao())

    private var taskLiveData: LiveData<List<TaskEntity>> =
        localRepository.getAllTaskLiveData(ORDER_BY_POSITION_DESC)

    var taskPairsLiveData: LiveData<List<Pair<Task, Int>>> = Transformations.map(taskLiveData) {
        return@map convertListTaskEntityToListPairsTask(it)
    }

    fun insertNewTaskToDB(task: Task) {
        Handler(handlerThread.looper)
            .post { localRepository.insertNewTaskToDB(convertTaskToEntity(task)) }
    }

    fun insertAllTasksInDB(listTask: List<Pair<Task, Int>>) {
        Handler(handlerThread.looper)
            .post { localRepository.insertAllTaskToDB(convertListPairsTaskEntityToListTask(listTask)) }
    }

    override fun onCleared() {
        super.onCleared()
        handlerThread.quitSafely()
    }
}