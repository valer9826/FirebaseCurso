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
            title = "tarea $randomValue",
            description = "Esto es una descripción",
        )
    }

    fun removeFromDatabase(reference: String) {
        myRef.child(reference).removeValue()
    }

    fun updateFromDatabase(reference: String) {
        myRef.child(reference).child("done").get().addOnSuccessListener { dataSnapshot ->
            // Este bloque se ejecuta si la recuperación fue exitosa
            if (dataSnapshot.exists()) {
                // Suponiendo que el valor de "done" es un booleano
                val isDone = dataSnapshot.getValue(Boolean::class.java) ?: false

                // Aquí puedes evaluar el valor de isDone
                if (isDone) {
                    myRef.child(reference).child("done").setValue(false)
                } else {
                    myRef.child(reference).child("done").setValue(true)
                }
            } else {
                // El nodo "done" no existe en la base de datos para la referencia dada
            }
        }.addOnFailureListener {
            // Este bloque se ejecuta si hubo un error al recuperar el valor
            // Maneja el error aquí
        }
    }

}