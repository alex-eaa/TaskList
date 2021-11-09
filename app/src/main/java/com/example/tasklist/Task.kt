package com.example.tasklist

data class Task(
    var id: Int? = null,
    var position: Int = -1,
    var title: String = "",
    var content: String = "",
    var isHighPriority: Boolean = false,
    var isCompleted: Boolean = false,
    var dateCreation: String = ""
)