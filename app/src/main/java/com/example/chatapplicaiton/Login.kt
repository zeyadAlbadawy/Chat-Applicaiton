package com.example.chatapplicaiton

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.*
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.awaitAll as awaitAll1

class Login : AppCompatActivity() {
    private lateinit var editEmail : EditText
    private lateinit var editPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var btnSignUp: Button
    private lateinit var resetpass: Button
    private lateinit var view: View
    private lateinit var auth : FirebaseAuth

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        view=findViewById(android.R.id.content)
        supportActionBar?.hide()
        resetpass = view.findViewById(R.id.resetpassword)
        resetpass.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        }
       //  mAuth = FirebaseAuth.getInstance()
        editEmail = findViewById(R.id.edit_email)
        editPassword = findViewById(R.id.edit_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)
        auth= Firebase.auth
        btnSignUp.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener{
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
                  Auth(email,password,this).log_in(view = view)
            if (auth.currentUser!!.isEmailVerified){
                Database().loginupdate()
            }

        }
    }


}