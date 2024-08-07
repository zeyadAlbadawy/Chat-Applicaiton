package com.example.chatapplicaiton

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.FileOutputStream

class Setting : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var userimage:ImageView
    private lateinit var storage: StorageReference
    @RequiresApi(Build.VERSION_CODES.P)
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val view =findViewById<View>(android.R.id.content)
            Storage().uploadimage(it,view,this)
            println("=============${it.path}")
            userimage.setImageURI(it)
        }
    }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        userimage=findViewById(R.id.addedimage)
        auth=Firebase.auth
        val logoutbtn =findViewById<Button>(R.id.logoutbtn)
        val addimagebtn =findViewById<Button>(R.id.changeimagebtn)

        logoutbtn.setOnClickListener{
            auth.signOut()
            val intent =Intent(this,Login::class.java).setAction("finish_activity")
            startActivity(intent)
            finishAffinity()
        }
        addimagebtn.setOnClickListener{
            pickImageLauncher.launch("image/*")
        }
    }
}