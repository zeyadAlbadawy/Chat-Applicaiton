package com.example.chatapplicaiton
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.values
import com.google.firebase.database.snapshots
import com.google.firebase.database.values
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import java.lang.reflect.Type

class Database {
    private val auth = Firebase.auth

    private var database =FirebaseDatabase.getInstance().getReference()
    fun userdata(name : String,password : String, email :String){
        val uservalues = hashMapOf("name" to name
            , "email" to email
            ,"password" to password
        )
        val child= database.child("/User")
        child.child(auth.uid.toString()).setValue(uservalues)


    }
    @Exclude
    fun loginupdate(){
        println(auth.currentUser!!.uid)
        database.child("User").child(auth.currentUser!!.uid).updateChildren(hashMapOf<String,Any>("is email verified" to auth.currentUser!!.isEmailVerified))
    }
fun getdata() : Map<String,ArrayList<String>>{

        var  listname = ArrayList<String>()
        var  listemail = ArrayList<String>()
        var  listuid = ArrayList<String>()
        println("geeeeeeeeeeeeeeeeeeeeeeeeeeeeeeet")
        val dataref= database.child("User")
        dataref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                println(error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                listname.clear()
                listemail.clear()
                     val snap= snapshot.getValue() as Map<String,Map<String,*>>
                for (i in snap.keys.toList()){
                    listuid.add(i.toString())
                }
                        var mp = snap.values
                for (i in mp){
                    listname.add(i["name"].toString())
                    listemail.add(i["email"].toString())
                }
                    println(listemail)
                    println(listname)
                    println(listuid)


            }
        })
        return mapOf<String,ArrayList<String>>("name" to listname,"email" to listemail,"uid" to listuid)
        }
}