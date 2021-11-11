package com.example.tasklist

import androidx.lifecycle.LiveData


const val ORDER_BY_POSITION = "ORDER_BY_POSITION"
const val ORDER_BY_POSITION_DESC = "ORDER_BY_POSITION_DESC"
const val ORDER_BY_TYPE = "ORDER_BY_TYPE"
const val ORDER_BY_TYPE_DESC = "ORDER_BY_TYPE_DESC"


interface LocalRepository {
    fun getAllTaskLiveData(orderBy: String): LiveData<List<TaskEntity>>
    fun insertNewTaskToDB(taskEntity: TaskEntity)
    fun saveAllTaskToDB(entity: List<TaskEntity>)
}