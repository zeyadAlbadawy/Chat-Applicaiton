package com.example.chatapplicaiton
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
class Auth(val email : String,val pass :String) {
    private val auth = Firebase.auth
     companion object{
         var error : String?=null
     }
    fun Log_in() : String{
        try {
            auth.signInWithEmailAndPassword(email,pass)
            Log.d("message","success")
        }catch (e :Error){
            Log.d("message","Fail")
            error=e.toString()
        }
        return error.toString()
    }

}