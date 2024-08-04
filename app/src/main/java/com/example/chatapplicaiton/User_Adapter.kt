package com.example.chatapplicaiton

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.content.Intent

class User_Adapter(val context: Context, val userList: ArrayList<User>) :
    RecyclerView.Adapter<User_Adapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.list_item, parent , false)
        return UserViewHolder(view)
    }
    override fun onBindViewHolder(holder:UserViewHolder, position: Int) {
     val currentUser = userList[position]
        holder.nameview.text = currentUser.name
        holder.emailview.text = currentUser.email
        holder.itemView.setOnClickListener{
                val intent = Intent(context, Setting::class.java)
                    .putExtra("name",currentUser.name)
                    .putExtra("email",currentUser.email)
                context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return userList.size
    }
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameview=itemView.findViewById<TextView>(R.id.nameview)
        val emailview=itemView.findViewById<TextView>(R.id.emailview)
    }
}