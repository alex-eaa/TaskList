package com.example.tasklist


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
    return list.zipWithNext { a, _ -> Pair(convertEntityToTask(a), ITEM_STATE_CLOSE) }
}

fun addToListPairsTaskHeader(listPairs: List<Pair<Task, Int>>): List<Pair<Task, Int>> {
    val listWithHeader = listPairs.toMutableList()
    listWithHeader.add(0, Task(type = TYPE_HEADER, title = "МОИ ЗАДАЧИ") to ITEM_STATE_CLOSE)
    return listWithHeader
}
