package com.example.chatapplicaiton

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Search : AppCompatActivity() {
    lateinit var customAdapter: User_Adapter
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        println(MainActivity.list)
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var userslist=ArrayList<User>()
        val recyclerView: RecyclerView = findViewById(R.id.searchview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val view : View = findViewById(android.R.id.content)
        customAdapter = User_Adapter(this,userslist ,view)
        recyclerView.adapter = customAdapter
        val searchbox: EditText = findViewById(R.id.searchbox)
        searchbox.doOnTextChanged { text, start, before, count ->
            run {
                userslist.clear()
                for (i in MainActivity.list) {

                        if (i.name.startsWith(text!!, ignoreCase = true) && !userslist.contains(i)) {
                            userslist.add(i)
                            customAdapter.notifyDataSetChanged()
                        }
                }
            }
                    if (searchbox.text.toString()==""){
                        userslist.clear()
                    }
        }
    }
}