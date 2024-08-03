package com.example.chatapplicaiton

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context

class User_Adapter(val context: Context, val userList: ArrayList<User>,private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<User_Adapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.list_item, parent , false)
        return UserViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder:UserViewHolder, position: Int) {
     val currentUser = userList[position]
        holder.nameview.text = currentUser.name
        holder.emailview.text = currentUser.email
    }

    override fun getItemCount(): Int {
       return userList.size
    }
    class UserViewHolder(itemView: View, private val itemClickListener: ItemClickListener) : RecyclerView.ViewHolder(itemView) ,  View.OnClickListener{
        val nameview=itemView.findViewById<TextView>(R.id.nameview)
        val emailview=itemView.findViewById<TextView>(R.id.emailview)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemClickListener.onItemClick(adapterPosition)


        }
    }
}