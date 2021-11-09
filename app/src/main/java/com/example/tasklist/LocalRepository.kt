package com.example.tasklist

import androidx.lifecycle.LiveData


const val ORDER_BY_POSITION = "ORDER_BY_POSITION"
const val ORDER_BY_POSITION_DESC = "ORDER_BY_POSITION_DESC"
const val ORDER_BY_DATE_CREATION = "ORDER_BY_DATE_CREATION"
const val ORDER_BY_DATE_CREATION_DESC = "ORDER_BY_DATE_CREATION_DESC"
const val ORDER_BY_IS_HIGH_PRIORITY = "ORDER_BY_IS_HIGH_PRIORITY"
const val ORDER_BY_IS_HIGH_PRIORITY_DESC = "ORDER_BY_IS_HIGH_PRIORITY_DESC"

interface LocalRepository {
    fun getAllTaskLiveData(orderBy: String): LiveData<List<TaskEntity>>
    fun saveTask(task: Task)
}