package com.example.tasklist

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tasklist.data.TYPE_HEADER
import com.example.tasklist.data.Task
import com.example.tasklist.data.TaskEntity
import com.example.tasklist.ui.ITEM_STATE_CLOSE


fun convertTaskToEntity(task: Task): TaskEntity {
    return TaskEntity(
        id = task.id,
        position = task.position,
        title = task.title,
        content = task.content,
        type = task.type,
        isCompleted = task.isCompleted.toString(),
        dateCreation = task.dateCreation,
    )
}


fun convertEntityToTask(taskEntity: TaskEntity): Task {
    return Task(
        id = taskEntity.id,
        position = taskEntity.position,
        title = taskEntity.title,
        content = taskEntity.content,
        type = taskEntity.type,
        isCompleted = taskEntity.isCompleted.toBoolean(),
        dateCreation = taskEntity.dateCreation,
    )
}


fun convertListTaskEntityToListPairsTask(list: List<TaskEntity>): List<Pair<Task, Int>> {
    val listPairs = mutableListOf<Pair<Task, Int>>()
    list.forEach {
        listPairs.add(Pair(convertEntityToTask(it), ITEM_STATE_CLOSE))
    }
    return listPairs
}

fun convertListPairsTaskEntityToListTask(listPairs: List<Pair<Task, Int>>): List<TaskEntity> {
    val list = mutableListOf<TaskEntity>()
    listPairs.forEachIndexed { index, taskEntity ->
        list.add(convertTaskToEntity(taskEntity.first))
    }
    return list
}

fun addHeader(listPairs: List<Pair<Task, Int>>): List<Pair<Task, Int>> {
    val listWithHeader = listPairs.toMutableList()
    listWithHeader.add(0, Task(id = -1, 0, type = TYPE_HEADER, title = "МОИ ЗАДАЧИ") to ITEM_STATE_CLOSE)
    return listWithHeader
}


fun delHeader(listPairs: List<Pair<Task, Int>>): List<Pair<Task, Int>> {
    val list = listPairs.toMutableList()
    list.removeAll {
            it.first.type == TYPE_HEADER
        }
    return list
}
