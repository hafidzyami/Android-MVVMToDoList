package com.example.roomlibrary.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDo::class], version = 1)
abstract class ToDoDatabase : RoomDatabase() {

    abstract val toDoDAO : ToDoDAO

    companion object{
        @Volatile
        private var INSTANCE : ToDoDatabase? = null
        fun getInstance(context: Context) : ToDoDatabase{
            var instance = INSTANCE
            if(instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "todo.db"
                ).build()
                INSTANCE = instance
            }
            INSTANCE = instance
            return instance
        }
    }
}