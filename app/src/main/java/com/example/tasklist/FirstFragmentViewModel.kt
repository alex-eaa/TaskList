package com.example.tasklist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tasklist.App.Companion.getTaskDao
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FirstFragmentViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val localRepository: LocalRepository = LocalRepositoryImpl(getTaskDao())

    val taskLiveData: MediatorLiveData<List<TaskEntity>> = MediatorLiveData<List<TaskEntity>>()

    var taskLiveData2: MutableLiveData<List<Pair<Task, Int>>> =
        MutableLiveData<List<Pair<Task, Int>>>()


    fun getAllTask(orderBy: String = ORDER_BY_POSITION_DESC) {
        taskLiveData.addSource(localRepository.getAllTaskLiveData(orderBy)) {
//            taskLiveData.value = it
            val newList = convertListTaskEntityToListPairsTask(it)
            taskLiveData2.postValue(addHeader(newList))
        }
    }

    fun insertTaskToDB(task: Task) {
        compositeDisposable.add(
            Observable
                .fromCallable { localRepository.insertNewTaskToDB(convertTaskToEntity(task)) }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun saveAllTasksToDB(list: List<Pair<Task, Int>>) {
        val listTask = convertListPairsTaskEntityToListTask(delHeader(list))
        compositeDisposable.add(
            Observable
                .fromCallable { localRepository.saveAllTaskToDB(listTask) }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun addNewTask(task: Task){
        taskLiveData2.value?.let {
            val list = it.toMutableList()
                list.add(task to ITEM_STATE_EDIT)
            taskLiveData2.postValue(list)
        }

    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}