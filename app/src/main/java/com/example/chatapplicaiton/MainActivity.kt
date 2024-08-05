package com.example.chatapplicaiton
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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
    lateinit var customAdapter: User_Adapter
        var list = ArrayList<User>()
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
        auth = Firebase.auth
        searchbtn = findViewById(R.id.searchbtn)
        settingbtn = findViewById(R.id.settingbtn)
        settingbtn.setOnClickListener {
            val intent=Intent(this,Setting::class.java)
            startActivity(intent)
        }
        val recyclerView: RecyclerView = findViewById(R.id.recycleview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        customAdapter = User_Adapter(this, list)
        recyclerView.adapter = customAdapter
        ordereddata()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun ordereddata() {
            val data = Database().getdata()
        lifecycleScope.launch {
            delay(1500)

            val names = data["name"] ?: emptyList()
            val emails = data["email"] ?: emptyList()
            withContext(Dispatchers.Default) {
                for (i in 0 until names.size) {
                    list.add(User(names[i], emails[i],""))
                }
            }
            withContext(Dispatchers.Main) {
                customAdapter.notifyDataSetChanged()
            }
        }
    }
}
