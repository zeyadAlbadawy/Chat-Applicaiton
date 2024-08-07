package com.example.chatapplicaiton

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class Storage {
    private lateinit var storage: StorageReference
    private lateinit var auth: FirebaseAuth
    fun uploadimage(uri: Uri,view: View,context: Context){
        storage= Firebase.storage.reference
        storage.child("userimage").child(auth.currentUser!!.uid).putFile(uri).addOnSuccessListener {
         Toast.makeText(context,"image added successfully",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            println("=======================Fail")
            view.findViewById<ImageView>(R.id.addedimage).setImageResource(R.drawable.user)
        }
    }
    fun getimage(context: Context,view: View){
        val image= storage.child("userimage").child(auth.currentUser!!.uid)

    }

}