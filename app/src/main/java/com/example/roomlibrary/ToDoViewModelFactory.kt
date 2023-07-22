package com.example.roomlibrary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomlibrary.db.ToDoRepository

class ToDoViewModelFactory(private val repository: ToDoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ToDoViewModel::class.java)){
            return ToDoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}