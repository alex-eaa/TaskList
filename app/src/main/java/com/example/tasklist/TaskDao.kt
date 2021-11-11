package com.example.tasklist

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Single

@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity ORDER BY id")
    fun allTaskById(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity ORDER BY id DESC")
    fun allTaskByIdDesc(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity ORDER BY type")
    fun allTaskByType(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity ORDER BY type DESC")
    fun allTaskByTypeDesc(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE isCompleted = 'true' ORDER BY id")
    fun completedTaskById(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE isCompleted = 'false' ORDER BY id")
    fun incompleteTaskById(): LiveData<List<TaskEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entity: List<TaskEntity>)

    @Update
    fun update(entity: TaskEntity)

    @Update
    fun updateAll(entity: List<TaskEntity>)

    @Query("DELETE FROM TaskEntity")
    fun deleteAll()

    @Transaction
    fun deleteAndCreate(entity: List<TaskEntity>) {
        deleteAll()
        insertAll(entity)
    }


//    @Query("SELECT * FROM TaskEntity WHERE idMovie = :idMovie")
//    fun getMovieRx(idMovie: Int): Single<TaskEntity>
}