package com.example.tasklist

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi


fun convertTaskToEntity(task: Task, index: Int? = null): TaskEntity {
    return TaskEntity(
        id = index,
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
        list.add(convertTaskToEntity(taskEntity.first, index))
    }
    return list
}

fun addHeader(listPairs: List<Pair<Task, Int>>): List<Pair<Task, Int>> {
    val listWithHeader = listPairs.toMutableList()
    listWithHeader.add(0, Task(id = -1, type = TYPE_HEADER, title = "МОИ ЗАДАЧИ") to ITEM_STATE_CLOSE)
    return listWithHeader
}

@RequiresApi(Build.VERSION_CODES.N)
fun delHeader(listPairs: List<Pair<Task, Int>>): List<Pair<Task, Int>> {
    val list = listPairs.toMutableList()
    list.removeIf {
            it.first.type == TYPE_HEADER
        }
    return list
}
