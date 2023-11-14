package com.learning.firebasecurso.realtimebasico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.learning.firebasecurso.databinding.ActivityMainBinding
import com.learning.firebasecurso.realtimebasico.data.Todo

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseInstance: FirebaseInstance
    private lateinit var todoAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseInstance = FirebaseInstance(this)
        setUpUI()
        setUpListener()
    }

    private fun setUpListener() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                val data: String? = snapshot.getValue<String>()
//
//                if (data != null) {
//                    binding.tvResult.text = data
//                }
                val data = getCleanSnapShot(snapshot)
                todoAdapter.setNewList(data)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Renato onCancelled", error.details)
            }
        }

        firebaseInstance.setupDatabaseListener(postListener)
    }

    private fun setUpUI() {
        binding.btnUpdate.setOnClickListener {
            firebaseInstance.writeOnFirebase()
        }
        todoAdapter = TodoAdapter()
        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }
    }

    private fun getCleanSnapShot(snapshot: DataSnapshot): List<Pair<String, Todo>> {
        val list = snapshot.children.map { item ->
            Pair(item.key!!, item.getValue(Todo::class.java)!!)
        }
        return list
    }

}