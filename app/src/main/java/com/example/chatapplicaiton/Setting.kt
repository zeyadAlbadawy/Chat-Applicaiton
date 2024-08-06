package com.example.chatapplicaiton

import MemoryCache
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ImageDecoder
import android.graphics.Paint
import android.graphics.RectF
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
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

class Setting : AppCompatActivity() {
    var auth: FirebaseAuth=Firebase.auth
    val imageView = findViewById<ImageView>(R.id.addedimage)
    private lateinit var storage: StorageReference
    @RequiresApi(Build.VERSION_CODES.P)
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            storage= Firebase.storage.reference
            storage.child("userimage").child(auth.currentUser!!.uid).putFile(it).addOnSuccessListener {
                println("=======================succes")
            }.addOnFailureListener{
                println("=======================Fail")
            }
            println("=============${it.path}")
            imageView.setImageURI(it)
        }
    }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (MemoryCache<String,Uri>(1024*1024).get("myimage") !=null){
            imageView.setImageURI(MemoryCache<String,Uri>(1024*1024).get("myimage"))
        }
        setContentView(R.layout.activity_setting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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