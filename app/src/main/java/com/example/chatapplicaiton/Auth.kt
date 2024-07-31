package com.example.chatapplicaiton
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
class Auth(val email : String ?,val pass :String ?) {
    private val auth = Firebase.auth
    companion object{
        var errorcode :String ?=null
        var signupresponse :Boolean=false
        var signinresponse :Boolean?=false
    }

    fun log_in() {
        if(email!=""&&pass !="") {
            auth.signInWithEmailAndPassword(email!!, pass!!).addOnCompleteListener {

                if (it.isSuccessful) {
                    signinresponse=it.isSuccessful
                    Log.d("message", "success")
                } else {
                    try {
                        throw it.exception ?: Exception("Firebase Exception")
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        val error = e.message
                        when (error) {
                            "The email address is badly formatted." -> {
                                errorcode = "The email address is badly formatted."
                            }

                            "Authentication failed: There is no user record corresponding to this identifier. The user may have been deleted." -> {
                                errorcode = "Authentication failed: There is no user record corresponding to this identifier. The user may have been deleted."
                            }

                            "The password is invalid or the user does not have a password." -> {
                                errorcode = "The password is invalid or the user does not have a password."
                            }


                        }
                    }
                }
            }
        }

    }
    fun signup(name : String){
        auth.createUserWithEmailAndPassword(email!!,pass!!).addOnCompleteListener{
            if(it.isSuccessful){
                signupresponse=it.isSuccessful
            }else{
                pass
            }
        }
    }
}