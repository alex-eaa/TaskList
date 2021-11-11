package com.example.tasklist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    @PrimaryKey (autoGenerate = true)
    var id: Int? = null,
    var title: String,
    var content: String,
    var type: Int,
    var isCompleted: String,
    var dateCreation: String
)
