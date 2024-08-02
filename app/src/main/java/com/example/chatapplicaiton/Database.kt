package com.example.chatapplicaiton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.values
import com.google.firebase.database.snapshots
import com.google.firebase.database.values
import com.google.firebase.ktx.Firebase

class Database {
    private val auth = Firebase.auth

    private var database =FirebaseDatabase.getInstance().getReference()
    fun userdata(name : String,password : String){
        val uservalues = hashMapOf("name" to name
            , "email" to auth.currentUser!!.email
            ,"id" to auth.currentUser!!.uid
            ,"password" to password
        )
        val child= database.child("/User")
        child.child(auth.currentUser!!.uid).setValue(uservalues)


    }
    fun loginupdate(){
        database.child("User").child(auth.currentUser!!.uid).updateChildren(hashMapOf<String,Any>("is email verified" to auth.currentUser!!.isEmailVerified))
    }
}