package com.example.tasklist

import androidx.lifecycle.LiveData
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


    fun getAllTask(orderBy: String = ORDER_BY_POSITION) {
        taskLiveData.addSource(localRepository.getAllTaskLiveData(orderBy)) {
            taskLiveData.value = it
        }
    }

    fun saveTaskToDB(task: Task) {
        compositeDisposable.add(
            Observable
                .fromCallable { localRepository.saveTask(task) }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}