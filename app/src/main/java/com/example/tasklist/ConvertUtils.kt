package com.example.tasklist


fun convertTaskToEntity(task: Task): TaskEntity {
    return TaskEntity(
        id = task.id,
        position = task.position,
        title = task.title,
        content = task.content,
        isHighPriority = task.isHighPriority.toString(),
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
        isHighPriority = taskEntity.isHighPriority.toBoolean(),
        isCompleted = taskEntity.isCompleted.toBoolean(),
        dateCreation = taskEntity.dateCreation,
    )
}
