package com.example.tasklist.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TaskEntity::class), version = 1, exportSchema = false)
abstract class TaskDataBase : RoomDatabase(){
    abstract fun taskDao(): TaskDao
}
