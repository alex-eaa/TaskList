package com.example.tasklist

import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.*
import com.example.tasklist.App.Companion.getTaskDao

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