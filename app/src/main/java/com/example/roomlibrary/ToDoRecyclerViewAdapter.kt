package com.example.roomlibrary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomlibrary.databinding.ListItemBinding
import com.example.roomlibrary.db.ToDo

class ToDoRecyclerViewAdapter(
    private val clickListener : (ToDo) -> Unit
) : RecyclerView.Adapter<MyViewHolder>(){

    private val todoList = ArrayList<ToDo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item,parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(todoList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun setList(toDo: List<ToDo>){
        todoList.clear()
        todoList.addAll(toDo)
    }
}

class MyViewHolder(val binding : ListItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(toDo: ToDo, clickListener: (ToDo) -> Unit){
        binding.textViewToDoName.text = toDo.name
        binding.textViewToDoDate.text = toDo.date
        binding.listItemLayout.setOnClickListener{
            clickListener(toDo)
        }

    }
}
