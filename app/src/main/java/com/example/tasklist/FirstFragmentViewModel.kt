package com.example.tasklist

import androidx.lifecycle.*
import com.example.tasklist.App.Companion.getTaskDao
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.nio.ByteOrder

class FirstFragmentViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val localRepository: LocalRepository = LocalRepositoryImpl(getTaskDao())

    private var taskLiveData: LiveData<List<TaskEntity>> =
        localRepository.getAllTaskLiveData(ORDER_BY_POSITION_DESC)

    var taskPairsLiveData: LiveData<List<Pair<Task, Int>>> = Transformations.map(taskLiveData) {
        return@map convertListTaskEntityToListPairsTask(it)
    }

    fun insertNewTaskToDB(task: Task) {
        compositeDisposable.add(
            Observable
                .fromCallable { localRepository.insertNewTaskToDB(convertTaskToEntity(task)) }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun insertAllTasksInDB(listTask: List<Pair<Task, Int>>) {
        compositeDisposable.add(
            Observable
                .fromCallable {
                    localRepository.insertAllTaskToDB(
                        convertListPairsTaskEntityToListTask(listTask)
                    )
                }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun deleteTaskFromDB(task: Task) {
        compositeDisposable.add(
            Observable
                .fromCallable { localRepository.deleteTask(convertTaskToEntity(task)) }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}