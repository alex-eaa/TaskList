package com.example.tasklist

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Single
import androidx.room.Update


@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity ORDER BY position")
    fun allTaskById(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity ORDER BY position DESC")
    fun allTaskByIdDesc(): LiveData<List<TaskEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: TaskEntity)

    @Update
    fun update(entity: TaskEntity)

    @Update
    fun updateEntities(vararg entities: TaskEntity)

    @Delete
    fun delete(entity: TaskEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entity: List<TaskEntity>)

    @Query("DELETE FROM TaskEntity")
    fun deleteAll()

    @Transaction
    fun deleteAndCreate(entity: List<TaskEntity>) {
        deleteAll()
        insertAll(entity)
    }
}