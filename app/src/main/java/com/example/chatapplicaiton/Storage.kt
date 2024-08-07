package com.example.chatapplicaiton

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Storage : AppCompatActivity(){
    private lateinit var storage: StorageReference
     var auth: FirebaseAuth=Firebase.auth
    fun uploadimage(uri: Uri,view: View,context: Context){
        storage= Firebase.storage.reference
        storage.child("userimage").child(auth.currentUser!!.uid).putFile(uri).addOnSuccessListener {
         Toast.makeText(context,"image added successfully",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            println("=======================Fail")
            view.findViewById<ImageView>(R.id.addedimage).setImageResource(R.drawable.user)
        }
    }
    fun getsettingimage(context: Context,view: View){
        storage= Firebase.storage.reference
        storage.child("userimage").child(auth.currentUser!!.uid).downloadUrl.addOnCompleteListener(){
            it.addOnSuccessListener {
                println("=======================${it}")
                println("=======================success")
                val imageView:ImageView=view.findViewById(R.id.addedimage)
                Glide.with(context).load(it).into(imageView)
            }

        }

    }
    fun putuserimage() : ArrayList<Map<String ,Uri>>{
        val urilist = ArrayList<Map<String ,Uri>>()
        val uidlist =Database().getdata()["uid"] ?: emptyList()
        storage= Firebase.storage.reference
        val ref= storage.child("userimage")
        println("successsssssssssssssssssssssssssssssssssssssssssssss")
        println(uidlist.size)
           for (i in uidlist){
               ref.child(i).downloadUrl.addOnSuccessListener {
                   urilist.add(mapOf(i to it))
               }
           }

    return urilist
}
}