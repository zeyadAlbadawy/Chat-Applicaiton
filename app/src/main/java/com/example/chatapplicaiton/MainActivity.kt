package com.example.chatapplicaiton

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(){
        val list = arrayListOf<User>(
            User("karim","karim@gmail.com","2352664"),
            User("karim","karim@gmail.com","2352664"),
            User("karim","karim@gmail.com","2352664"),
            User("karim","karim@gmail.com","2352664"),
            User("karim","karim@gmail.com","2352664"),
            User("karim","karim@gmail.com","2352664"),
        )
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val customAdapter = User_Adapter(this, list)
        val recyclerView: RecyclerView = findViewById(R.id.recycleview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customAdapter
        val lview = findViewById<RecyclerView>(R.id.recycleview)
        lview.setOnClickListener{
            Toast.makeText(this,"karim", Toast.LENGTH_SHORT).show()
        }

    }

}