package com.example.chatapplicaiton
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Auth(val email : String ?,val pass :String ?) {
    private val auth = Firebase.auth

    fun log_in(view: View,context: Context) : Unit{
        val errormail :TextView = view.findViewById(R.id.incorrectemail)
        val errorpass :TextView = view.findViewById(R.id.incorrectpass)
        val resetpass :TextView = view.findViewById(R.id.resetpassword)
        resetpass.setOnClickListener{
            val intent = Intent(Login(),MainActivity ::class.java)
            context.startActivity(intent)
        }
        errormail.visibility=View.INVISIBLE
        errorpass.visibility=View.INVISIBLE

        if (email != "" && pass != "") {
            auth.signInWithEmailAndPassword(email!!, pass!!).addOnCompleteListener {
                var vis: Boolean = false
                if (it.isSuccessful) {
                    Log.d("message", "success")
                } else {
                    val error = it.exception!!.message
                    when (error) {
                        "The email address is badly formatted." -> {
                            errormail.visibility=View.VISIBLE
                        }

                        "Authentication failed: There is no user record corresponding to this identifier. The user may have been deleted." -> {

                        }

                        "The password is invalid or the user does not have a password." -> {
                           errorpass.text="please enter the correct password"
                            errorpass.visibility=View.VISIBLE
                        }


                    }
                    print("================================")

                }
            }

        } else {

        }

    }
}