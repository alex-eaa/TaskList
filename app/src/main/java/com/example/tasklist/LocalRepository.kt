package com.example.tasklist

import androidx.lifecycle.LiveData

const val ORDER_BY_POSITION = "ORDER_BY_POSITION"
const val ORDER_BY_POSITION_DESC = "ORDER_BY_POSITION_DESC"

interface LocalRepository {
    fun getAllTaskLiveData(orderBy: String): LiveData<List<TaskEntity>>
    fun insertNewTaskToDB(taskEntity: TaskEntity)
    fun insertAllTaskToDB(listEntity: List<TaskEntity>)
    fun deleteTask(entity: TaskEntity)
}