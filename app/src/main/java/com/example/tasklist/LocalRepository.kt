package com.example.tasklist

import androidx.lifecycle.LiveData


const val ORDER_BY_POSITION = "ORDER_BY_POSITION"
const val ORDER_BY_POSITION_DESC = "ORDER_BY_POSITION_DESC"
const val ORDER_BY_DATE_CREATION = "ORDER_BY_DATE_CREATION"
const val ORDER_BY_DATE_CREATION_DESC = "ORDER_BY_DATE_CREATION_DESC"
const val ORDER_BY_TYPE = "ORDER_BY_TYPE"
const val ORDER_BY_TYPE_DESC = "ORDER_BY_TYPE_DESC"
const val ORDER_BY_TITLE = "ORDER_BY_TITLE"
const val ORDER_BY_TITLE_DESC = "ORDER_BY_TITLE_DESC"

interface LocalRepository {
    fun getAllTaskLiveData(orderBy: String): LiveData<List<TaskEntity>>
    fun saveTask(task: Task)
}