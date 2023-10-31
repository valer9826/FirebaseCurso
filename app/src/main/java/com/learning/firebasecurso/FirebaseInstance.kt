package com.learning.firebasecurso

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class FirebaseInstance(
    context: Context
) {

    private val database = Firebase.database

    init {
        FirebaseApp.initializeApp(context)
    }

    fun writeOnFirebase() {
        val myRef = database.reference
        val random = Random.nextInt(1, 200).toString()
        myRef.setValue("Mi primera escritura: $random")
    }

}