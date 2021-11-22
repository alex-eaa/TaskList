package com.example.tasklist

import android.app.Application
import androidx.room.Room
import com.example.tasklist.data.TaskDao
import com.example.tasklist.data.TaskDataBase


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: TaskDataBase? = null
        private const val DB_NAME = "Task.db"

        fun getTaskDao(): TaskDao {
            if (db == null) {
                synchronized(TaskDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw
                        IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            TaskDataBase::class.java,
                            DB_NAME
                        )
//                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return db!!.taskDao()
        }
    }
}