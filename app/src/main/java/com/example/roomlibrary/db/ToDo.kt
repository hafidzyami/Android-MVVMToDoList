package com.example.roomlibrary.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todo_id")
    val id : Int,

    @ColumnInfo(name = "todo_name")
    var name : String,

    @ColumnInfo(name = "todo_date")
    var date : String
) {
}