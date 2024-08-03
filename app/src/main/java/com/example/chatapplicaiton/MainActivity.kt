package com.example.chatapplicaiton

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(){
    lateinit var auth : FirebaseAuth
    lateinit var searchbtn : ImageButton
    lateinit var list : ArrayList<User>
    @SuppressLint("WrongViewCast", "MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth=Firebase.auth
        searchbtn=findViewById(R.id.searchbtn)
        searchbtn.setOnClickListener{
            auth.signOut()
        }
        ordereddata()
        val customAdapter = User_Adapter(this, list)
        val recyclerView: RecyclerView = findViewById(R.id.recycleview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customAdapter
        val lview = findViewById<RecyclerView>(R.id.recycleview)
        lview.setOnClickListener{
            Toast.makeText(this,"karim", Toast.LENGTH_SHORT).show()
        }

    }
    fun ordereddata(){
        var name : String
        var email : String
        val data =Database().getdata() as List<Map<*,*>>
        println("===============================")
        println(data)
        for(i in data){
            name= i["name"].toString()
            email = i["email"].toString()
            list.add(User(name,email))
        }
    }
}