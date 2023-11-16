package com.learning.firebasecurso.realtimebasico

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.learning.firebasecurso.R
import com.learning.firebasecurso.databinding.ItemTodoBinding
import com.learning.firebasecurso.realtimebasico.data.Todo

class TodoAdapter(
    private var todoList: List<Pair<String, Todo>> = emptyList(),
    private val onItemSelected: (Actions, String) -> Unit
) :
    RecyclerView.Adapter<TodoViewHolder>() {

    fun setNewList(newList: List<Pair<String, Todo>>) {
//        todoList = newList
//        notifyDataSetChanged()
        val diffResult = DiffUtil.calculateDiff(TodoDiffCallback(todoList, newList))
        todoList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todoList[position], onItemSelected)
    }

}

class TodoDiffCallback(
    private val oldList: List<Pair<String, Todo>>,
    private val newList: List<Pair<String, Todo>>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].second.title == newList[newItemPosition].second.title
    }

}

class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemTodoBinding.bind(itemView)
    fun bind(todoTask: Pair<String, Todo>, onItemSelected: (Actions, String) -> Unit) {
        binding.tvTitle.text = todoTask.second.title
        binding.tvDescription.text = todoTask.second.description
        binding.tvReference.text = todoTask.first
        binding.ivDone.setOnClickListener { onItemSelected(Actions.DONE, todoTask.first) }
        binding.ivDelete.setOnClickListener { onItemSelected(Actions.DELETE, todoTask.first) }
        val color = if (todoTask.second.done == true) {
            R.color.gold
        } else {
            R.color.black
        }
        binding.cvItem.strokeColor = ContextCompat.getColor(binding.cvItem.context, color)
    }

}