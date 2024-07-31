package com.example.chatapplicaiton

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUp : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var editEmail : EditText
    private lateinit var editPassword : EditText
    private lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()
       // mAuth = FirebaseAuth.getInstance()
        editEmail = findViewById(R.id.edit_email)
        editPassword = findViewById(R.id.edit_password)
        editName = findViewById(R.id.edit_name)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnSignUp.setOnClickListener{
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            SignUp(email,password);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @SuppressLint("NotConstructor")
    private fun SignUp(email: String, password: String) {
        // logic of creating user
       // mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){ task ->
         //   if(task.isSuccessful){
         //       val intent = Intent (this@SignUp, MainActivity::class.java)
         //       startActivity(intent)
         //   }else{
         //       Toast.makeText(this@SignUp , "some error occurred" , Toast.LENGTH_SHORT).show()
          //  }
    //}
    }
}