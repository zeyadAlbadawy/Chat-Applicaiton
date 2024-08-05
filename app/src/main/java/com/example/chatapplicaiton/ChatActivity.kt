package com.example.chatapplicaiton

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var messagesRecycler: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference
    private lateinit var senderNode: String
    private lateinit var receiverNode: String
    private lateinit var sendbutton : ImageView
    private lateinit var messagebox :EditText

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        messageList = ArrayList()
        messagesRecycler = findViewById(R.id.messages)
        messagesRecycler.layoutManager = LinearLayoutManager(this)
        messageAdapter= MessageAdapter(this,messageList)
        messagesRecycler.adapter = messageAdapter
        // Initialize Firebase Database reference
        mDbRef = FirebaseDatabase.getInstance().reference

        // Get sender and receiver UIDs (customize based on your logic)
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        val receiverUid = intent.getStringExtra("uid")

        // Create unique chat nodes for sender and receiver
        senderNode = "$receiverUid$senderUid"
        receiverNode = "$senderUid$receiverUid"
        // Initialize RecyclerView and adapter
        sendbutton =findViewById(R.id.sendBtn)
        messagebox=findViewById(R.id.messageBox)
        mDbRef.child("chats").child(senderNode).child("messages")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                        println("======================we are in Datachange")
                    println(messageList.size)
                    messageAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })

        sendbutton.setOnClickListener{
            val message=messagebox.text.toString()
            val messageobject=Message(message,senderUid)
            mDbRef.child("chats").child(senderNode).child("messages").push().setValue(messageobject).addOnSuccessListener {
                mDbRef.child("chats").child(receiverNode).child("messages").push().setValue(messageobject)

            }
            messagebox.setText("")
        }
        // Listen for new messages in the database
        // Additional logic for sending messages can be added here
        // For example, handling user input and updating the database
    }
}
