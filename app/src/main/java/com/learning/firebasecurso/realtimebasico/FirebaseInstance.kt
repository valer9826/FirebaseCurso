package com.learning.firebasecurso.realtimebasico

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.learning.firebasecurso.realtimebasico.data.Todo
import kotlin.random.Random

class FirebaseInstance(
    context: Context
) {

    private val database = Firebase.database
    private val myRef = database.reference


    init {
        FirebaseApp.initializeApp(context)
    }

    fun writeOnFirebase() {
        val random = Random.nextInt(1, 200).toString()
//        myRef.setValue("Mi primera escritura: $random")
        val newItem = myRef.push()
        newItem.setValue(getGenericTodoTaskItem(random))
    }

    fun setupDatabaseListener(postListener: ValueEventListener) {
        database.reference.addValueEventListener(postListener)
    }

    private fun getGenericTodoTaskItem(randomValue: String): Todo {
        return Todo(
            title= "tarea $randomValue",
            description = "Esto es una descripción",
        )
    }

    fun removeFromDatabase(reference: String) {
        myRef.child(reference).removeValue()
    }

}