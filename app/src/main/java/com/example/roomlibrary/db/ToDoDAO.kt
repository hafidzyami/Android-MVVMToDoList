package com.example.roomlibrary.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDAO {

    @Upsert
    suspend fun upsert(toDo: ToDo)

    @Insert
    suspend fun insert(toDo: ToDo)

    @Update
    suspend fun update(toDo : ToDo)

    @Delete
    suspend fun delete(toDo: ToDo)

    @Query("SELECT * FROM todo_table")
    fun getAll() : LiveData<List<ToDo>>

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()
}