package com.example.chatapplicaiton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Database {
    private val auth = Firebase.auth

    private lateinit var database : DatabaseReference
    fun userdata(name : String, email : String,password : String){
    database = Firebase.database.reference
        database.child("/User").child(name).setValue({"name" to name ; "email" to email; "password" to password})

    }
}