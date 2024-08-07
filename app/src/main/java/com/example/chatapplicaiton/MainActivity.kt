package com.example.chatapplicaiton
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var searchbtn: ImageButton
    lateinit var settingbtn :ImageButton
    companion object {
    lateinit var customAdapter: User_Adapter
        var list = ArrayList<User>()
    }
    @SuppressLint("WrongViewCast", "MissingInflatedId", "CutPasteId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
       val view : View = findViewById(android.R.id.content)
        auth = Firebase.auth
        searchbtn = findViewById(R.id.searchbtn)
        settingbtn = findViewById(R.id.settingbtn)
        searchbtn.setOnClickListener{
            val intent=Intent(this,Search::class.java)
            startActivity(intent)
        }
        settingbtn.setOnClickListener {
            val intent=Intent(this,Setting::class.java)
            startActivity(intent)
        }
        val recyclerView: RecyclerView = findViewById(R.id.recycleview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        customAdapter = User_Adapter(this, list,view)
        recyclerView.adapter = customAdapter
        ordereddata()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun ordereddata() {
            val data = Database().getdata(this)
        lifecycleScope.launch {
            delay(2000)
            val images= Storage().putUserImage(data["uid"] !!)
            delay(500)
            println("images=====================$images")
            val names = data["name"] ?: emptyList()
            val emails = data["email"] ?: emptyList()
            val uid = data["uid"] ?: emptyList()
            withContext(Dispatchers.Default) {
                for (i in 0 until uid.size) {
                    if(i<= images.size -1){
                    list.add(User(names[i], emails[i],uid[i],images[i][uid[i]] !!))
                    }
                    else{
                        list.add(User(names[i], emails[i],uid[i],"https://firebasestorage.googleapis.com/v0/b/chat-application-8a1d7.appspot.com/o/userimage%2Fuser.png?alt=media&token=1d4272bd-0f9e-4e67-9738-727ce232ad2c".toUri()))
                    }                }
            }
            withContext(Dispatchers.Main) {
                customAdapter.notifyDataSetChanged()
            }
        }
    }
}
