package com.example.chatapplicaiton
import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
class Auth(val email : String,val pass :String) {
    private val auth = Firebase.auth
    companion object{
        var errormap :Map<String,String> ?=null
    }

    fun Log_in() {

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {

            if (it.isSuccessful) {
                Log.d("message", "success")
            }else {
                try {
                    throw it.exception ?: Exception("Firebase Exception")
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    val error = e.message
                    when(error){
                        "The email address is badly formatted." -> {
                            errormap[""]="please enter invalid email"}
                        "Authentication failed: There is no user record corresponding to this identifier. The user may have been deleted." -> {}
                        "The password is invalid or the user does not have a password." -> {}

                    }
                }
            }
        }

    }
}