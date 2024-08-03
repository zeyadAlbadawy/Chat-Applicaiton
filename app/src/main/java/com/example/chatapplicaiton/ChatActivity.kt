package com.example.chatapplicaiton

import android.os.Bundle
//import android.os.Message this was imported instead of the created message class
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.chatapplicaiton.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {

    private lateinit var messagesRecycler : RecyclerView
    private lateinit var messageBox : EditText
    private lateinit var sendBtn : ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList : ArrayList<Message>
    private lateinit var mDbRef : DatabaseReference

    var reciverNode :String? =null
    var senderNode :String? =null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mDbRef = FirebaseDatabase.getInstance().getReference()
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        val name=intent.getStringExtra("name")
        val reciverUid=intent.getStringExtra("uid")
        senderNode = reciverUid + senderUid
       reciverNode = senderUid  + reciverUid



         supportActionBar?.title = name

        messagesRecycler.layoutManager =LinearLayoutManager(this)
        messagesRecycler.adapter=messageAdapter
        //show messages to the user
        mDbRef.child("chats").child(senderNode!!).child("messages")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapShot in snapshot.children){
                        val message = postSnapShot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }


            })






        messagesRecycler = findViewById(R.id.messages)
        messageBox = findViewById(R.id.messageBox)
        sendBtn = findViewById(R.id.sendBtn)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)

        //updating DB, with new messages
        sendBtn.setOnClickListener{
            val message = messageBox.text.toString()
            val messageObject = Message(message,senderUid)
            mDbRef.child("chats").child(senderNode!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(reciverNode!!).child("messages").push()
                        .setValue(messageObject)

                }
            messageBox.setText("")
        }



    }
}