package com.example.tasklist

import androidx.lifecycle.LiveData

class LocalRepositoryImpl(private val localDataSource: TaskDao) : LocalRepository {

    override fun getAllTaskLiveData(orderBy: String): LiveData<List<TaskEntity>> {
        return when (orderBy) {
            ORDER_BY_POSITION -> localDataSource.allTaskById()
            ORDER_BY_POSITION_DESC -> localDataSource.allTaskByIdDesc()
            ORDER_BY_TYPE -> localDataSource.allTaskByType()
            ORDER_BY_TYPE_DESC -> localDataSource.allTaskByTypeDesc()
            else -> localDataSource.allTaskById()
        }
    }

    override fun insertNewTaskToDB(taskEntity: TaskEntity) {
        localDataSource.insert(taskEntity)
    }

    override fun saveAllTaskToDB(taskEntity: List<TaskEntity>) {
        localDataSource.deleteAndCreate(taskEntity)
    }

}