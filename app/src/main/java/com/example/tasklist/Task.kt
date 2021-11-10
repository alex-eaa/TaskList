package com.example.tasklist

const val TYPE_STANDARD_PRIORITY = 0
const val TYPE_HIGH_PRIORITY = 1
const val TYPE_HEADER = 2

data class Task(
    var id: Int? = null,
    var position: Int = 1,
    var title: String = "",
    var content: String = "",
    var type: Int = TYPE_STANDARD_PRIORITY,
    var isCompleted: Boolean = false,
    var dateCreation: String = ""
)