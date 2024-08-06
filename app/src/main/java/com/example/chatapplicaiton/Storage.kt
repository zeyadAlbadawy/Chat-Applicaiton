package com.example.chatapplicaiton

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.storage

class Storage {
    private lateinit var storage: FirebaseStorage
    fun storeimage(){
        storage= Firebase.storage("gs://chat-application-8a1d7.appspot.com/userimage/")
    }
}