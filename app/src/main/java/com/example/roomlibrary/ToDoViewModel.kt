package com.example.roomlibrary

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomlibrary.db.ToDo
import com.example.roomlibrary.db.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToDoViewModel(private val repository: ToDoRepository) : ViewModel() {

    val todoList = repository.todoList

    private var isUpdateOrDelete = false
    private lateinit var todoUpdateOrDelete : ToDo

    val inputName = MutableLiveData<String>()
    val inputDate = MutableLiveData<String>()

    val saveUpdateBtn = MutableLiveData<String>()
    val deleteOrAllBtn = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
        get() = statusMessage

    init {
        saveUpdateBtn.value = "Save"
        deleteOrAllBtn.value = "Clear All"
    }

    fun clearAllOrDelete(){
        if(isUpdateOrDelete){
            delete(todoUpdateOrDelete)
        }
        else{
            clearAll()
        }
    }

    fun saveOrUpdate(){
        if(isUpdateOrDelete){
            todoUpdateOrDelete.name = inputName.value!!
            todoUpdateOrDelete.date = inputDate.value!!
            update(todoUpdateOrDelete)
        }
        else{
            val name = inputName.value!!
            val date = inputDate.value!!
            insert(ToDo(0, name, date))
            inputName.value = ""
            inputDate.value = ""
        }
    }

    fun deleteOrClear(){
        if(isUpdateOrDelete){
            todoUpdateOrDelete.name = inputName.value!!
            todoUpdateOrDelete.date = inputDate.value!!
            delete(todoUpdateOrDelete)
        }
        else{
            clearAll()
        }
    }

    fun insert(toDo: ToDo){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(toDo)
            withContext(Dispatchers.Main){
                statusMessage.value = Event("Insert Successfully")
            }
        }
    }

    fun delete(toDo: ToDo){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(toDo)
            withContext(Dispatchers.Main){
                inputName.value = ""
                inputDate.value = ""
                isUpdateOrDelete = false
                saveUpdateBtn.value = "Save"
                deleteOrAllBtn.value = "Clear All"
                statusMessage.value = Event("Delete Successfully")
            }
        }
    }

    fun update(toDo: ToDo){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(toDo)
            withContext(Dispatchers.Main){
                inputName.value = ""
                inputDate.value = ""
                isUpdateOrDelete = false
                saveUpdateBtn.value = "Save"
                deleteOrAllBtn.value = "Clear All"
                statusMessage.value = Event("Update Successfully")

            }
        }
    }

    fun clearAll(){
        viewModelScope.launch(Dispatchers.IO){
            repository.clearAll()
            withContext(Dispatchers.Main){
                statusMessage.value = Event("Clear All Successfully")
            }
        }
    }

    fun initUpdateAndDelete(toDo: ToDo){
        inputName.value = toDo.name
        inputDate.value = toDo.date
        isUpdateOrDelete = true
        todoUpdateOrDelete = toDo
        saveUpdateBtn.value = "Update"
        deleteOrAllBtn.value = "Delete"
    }





}