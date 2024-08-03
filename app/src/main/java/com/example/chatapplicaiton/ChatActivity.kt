package com.example.chatapplicaiton

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class ChatActivity : AppCompatActivity() {
    private lateinit var messagesRecycler : RecyclerView
    private lateinit var messageBox : EditText
    private lateinit var sendBtn : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val name=intent.getStringExtra("name")
        val uid=intent.getStringExtra("uid")
         supportActionBar?.title = name

        messagesRecycler = findViewById(R.id.messages)
        messageBox = findViewById(R.id.messageBox)
        sendBtn = findViewById(R.id.sendBtn)




    }
}