package com.example.roomlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomlibrary.databinding.ActivityMainBinding
import com.example.roomlibrary.db.ToDo
import com.example.roomlibrary.db.ToDoDAO
import com.example.roomlibrary.db.ToDoDatabase
import com.example.roomlibrary.db.ToDoRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var toDoViewModel: ToDoViewModel
    private lateinit var adapter : ToDoRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = ToDoDatabase.getInstance(application).toDoDAO
        val repository = ToDoRepository(dao)
        val factory = ToDoViewModelFactory(repository)
        toDoViewModel = ViewModelProvider(this, factory)[ToDoViewModel::class.java]

        binding.myViewModel = toDoViewModel
        binding.lifecycleOwner = this

        initRecyclerView()

        toDoViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayToDoList(){
        toDoViewModel.todoList.observe(this, Observer {
            Log.i("MyTag", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun initRecyclerView(){
        binding.recyclerViewTodo.layoutManager = LinearLayoutManager(this)
        adapter = ToDoRecyclerViewAdapter({selectedItem : ToDo -> listItemClicked(selectedItem)})
        binding.recyclerViewTodo.adapter = adapter
        displayToDoList()
    }

    private fun listItemClicked(toDo: ToDo){
        toDoViewModel.initUpdateAndDelete(toDo)
    }
}