package com.example.tasklist

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Single

@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity ORDER BY position")
    fun allTaskByPosition(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity ORDER BY position DESC")
    fun allTaskByPositionDesc(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity ORDER BY title")
    fun allTaskByTitle(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity ORDER BY title DESC")
    fun allTaskByTitleDesc(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity ORDER BY dateCreation")
    fun allTaskByDate(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity ORDER BY dateCreation DESC")
    fun allTaskByDateDesc(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity ORDER BY type")
    fun allTaskByType(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity ORDER BY type DESC")
    fun allTaskByTypeDesc(): LiveData<List<TaskEntity>>


    @Query("SELECT * FROM TaskEntity WHERE isCompleted = 'true' ORDER BY position")
    fun completedTaskByPosition(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE isCompleted = 'false' ORDER BY position")
    fun incompleteTaskByPosition(): LiveData<List<TaskEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: TaskEntity)

    @Update
    fun update(entity: TaskEntity)

    @Delete
    fun delete(entity: TaskEntity)


//    @Query("SELECT * FROM TaskEntity WHERE idMovie = :idMovie")
//    fun getMovieRx(idMovie: Int): Single<TaskEntity>
}