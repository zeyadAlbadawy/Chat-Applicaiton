package com.example.chatapplicaiton
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

class Auth(val email : String ?,val pass :String ?,val context: Context) {
    private val auth = Firebase.auth

    @SuppressLint("SetTextI18n")
    fun log_in(view: View) : Unit{
        val errormail :TextView = view.findViewById(R.id.incorrectemail)
        val errorpass :TextView = view.findViewById(R.id.incorrectpass)
        val empty :TextView = view.findViewById(R.id.empty)
        errormail.visibility=View.INVISIBLE
        errorpass.visibility=View.INVISIBLE
        empty.visibility=View.INVISIBLE

        if (email!!.isEmpty()||pass!!.isEmpty())
        {
            empty.text="please enter the empty field"
            empty.visibility=View.VISIBLE
            println("=============================")
        }
        else {
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                var vis: Boolean = false
                if (it.isSuccessful) {
                    if(auth.currentUser!!.isEmailVerified){
                    val  intent =Intent(context,MainActivity ::class.java)
                    context.startActivity(intent)
                    }else{
                        Toast.makeText(context,"please check inbox and verfy your email",Toast.LENGTH_SHORT).show()
                    }
                    Log.d("message", "success")
                } else {

                    val error = it.exception!!.message.toString()
                    println("===================$error")
                    when (error) {
                        "The email address is badly formatted." -> {
                            errormail.visibility=View.VISIBLE
                        }

                        "There is no user record corresponding to this identifier. The user may have been deleted." -> {

                            errormail.visibility=View.VISIBLE
                        }

                        "The password is invalid or the user does not have a password." -> {
                           errorpass.text="please enter the correct password"
                            errorpass.visibility=View.VISIBLE
                            println("heloooooooooooooooooooooooo")
                        }


                    }
                    print("================================")

                }
            }

        }

    }
    @SuppressLint("SuspiciousIndentation")
    fun signup(view: View){
        val errormessage=view.findViewById<TextView>(R.id.errorview)
        val name : TextView=view.findViewById(R.id.edit_name)
        if (name.text.length>=5){
            if(email!!.endsWith("@gmail.com")){
            val sub1=email.substring(0,email.indexOf("@"))
                if(sub1.length >=5){
                    println("================================")
                    strongpass(pass.toString(),view)
                }
            }else{
                errormessage.text="please enter valid email"
                errormessage.visibility=View.VISIBLE
            }
        }else{
            errormessage.text="your name must have more than 5 character"
            errormessage.visibility=View.VISIBLE
        }

    }

    fun strongpass(password : String, view: View){
            val errormessage=view.findViewById<TextView>(R.id.errorview)
        val specialchars = arrayOf("!","@","#","$","%","^","&","*","(",")","_")
        var count : Int=0
        if(password.length>=10) {
            for (i in password) {
                if (specialchars.contains(i.toString())) {
                    count += 1

                }
            }
            if (count >= 1) {
                auth.createUserWithEmailAndPassword(email!!, pass!!).addOnCompleteListener {
                    auth.currentUser!!.sendEmailVerification()
                    Toast.makeText(context,"please check inbox to verify your email",Toast.LENGTH_SHORT).show()
                    val intent=Intent(context,Login :: class.java)
                    context.startActivity(intent)
                }


            }else{
                errormessage.text="password must have at least one character"
                errormessage.visibility=View.VISIBLE
            }
        }

        else{
            errormessage.text="password must have at least9 numbers and one character"
            errormessage.visibility=View.VISIBLE
        }
    }
}