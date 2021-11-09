package com.example.tasklist

import androidx.lifecycle.LiveData

class LocalRepositoryImpl(private val localDataSource: TaskDao) : LocalRepository {

    override fun getAllTaskLiveData(orderBy: String): LiveData<List<TaskEntity>> {
        return when (orderBy) {
            ORDER_BY_POSITION -> localDataSource.allTaskByPosition()
            ORDER_BY_POSITION_DESC -> localDataSource.allTaskByPositionDesc()
            ORDER_BY_DATE_CREATION -> localDataSource.allTaskByDate()
            ORDER_BY_DATE_CREATION_DESC -> localDataSource.allTaskByDateDesc()
            ORDER_BY_IS_HIGH_PRIORITY -> localDataSource.allTaskByPriority()
            ORDER_BY_IS_HIGH_PRIORITY_DESC -> localDataSource.allTaskByPriorityDesc()
            else -> localDataSource.allTaskByPosition()
        }
    }

    override fun saveTask(task: Task) {
        localDataSource.insert(convertTaskToEntity(task))
    }
}