package com.example.tasklist.repository

import androidx.lifecycle.LiveData
import com.example.tasklist.data.TaskEntity

const val ORDER_BY_POSITION = "ORDER_BY_POSITION"
const val ORDER_BY_POSITION_DESC = "ORDER_BY_POSITION_DESC"

interface LocalRepository {
    fun getAllTaskLiveData(orderBy: String): LiveData<List<TaskEntity>>
    fun insertNewTaskToDB(taskEntity: TaskEntity)
    fun insertAllTaskToDB(listEntity: List<TaskEntity>)
    fun deleteTask(entity: TaskEntity)
}