package com.example.chatapplicaiton
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.values
import com.google.firebase.database.snapshots
import com.google.firebase.database.values
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class Database {
    private val auth = Firebase.auth

    private var database =FirebaseDatabase.getInstance().getReference()
    fun userdata(name : String,password : String, email :String){
        val uservalues = hashMapOf("name" to name
            , "email" to email
            ,"password" to password
        )
        val child= database.child("/User")
        child.child(auth.uid.toString()).setValue(uservalues)


    }
    @Exclude
    fun loginupdate(){
        database.child("User").child(auth.currentUser!!.uid).updateChildren(hashMapOf<String,Any>("is email verified" to auth.currentUser!!.isEmailVerified))
    }
    fun getdata() : ArrayList<Task<DataSnapshot>> {
        println("geeeeeeeeeeeeeeeeeeeeeeeeeeeeeeet")
        val mapdata= arrayListOf(database.child("User").get().addOnSuccessListener { println(
            "======================"
        )
        println(it)
        }
            .addOnFailureListener{
                it.printStackTrace()
            })
        return mapdata
    }
}