package com.example.tasklist

import androidx.lifecycle.LiveData

class LocalRepositoryImpl(private val localDataSource: TaskDao) : LocalRepository {

    override fun getAllTaskLiveData(orderBy: String): LiveData<List<TaskEntity>> {
        return when (orderBy) {
            ORDER_BY_POSITION -> localDataSource.allTaskById()
            ORDER_BY_POSITION_DESC -> localDataSource.allTaskByIdDesc()
            else -> localDataSource.allTaskById()
        }
    }

    override fun insertNewTaskToDB(taskEntity: TaskEntity) {
        localDataSource.insert(taskEntity)
    }

    override fun insertAllTaskToDB(listEntity: List<TaskEntity>) {
        localDataSource.deleteAndCreate(listEntity)
    }

    override fun deleteTask(entity: TaskEntity){
        localDataSource.delete(entity)
    }
}