package com.example.roomlibrary.db

class ToDoRepository(private val dao : ToDoDAO) {

    val todoList = dao.getAll()

    suspend fun insert(toDo: ToDo){
        dao.insert(toDo)
    }

    suspend fun upsert(toDo: ToDo){
        dao.upsert(toDo)
    }

    suspend fun update(toDo: ToDo){
        dao.update(toDo)
    }

    suspend fun delete(toDo: ToDo){
        dao.delete(toDo)
    }

    suspend fun clearAll(){
        dao.deleteAll()
    }

}