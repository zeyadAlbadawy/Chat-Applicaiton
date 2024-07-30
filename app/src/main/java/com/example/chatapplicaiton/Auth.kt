package com.example.chatapplicaiton
import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
class Auth(val email : String,val pass :String) {
    private val auth = Firebase.auth
     companion object{
         var error : AuthResult?=null
     }
    fun Log_in() : String {

        val credential = auth.signInWithEmailAndPassword(email, pass)
        if (credential.isSuccessful) {
            Log.d("message", "success")
        }else{
            error= credential.result
            Log.d("message", error.toString())
        }
     return error.toString()
    }

}