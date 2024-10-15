package com.example.myapplication1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(val context: Context,val userList: ArrayList<User>)
    :RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var toolbar: Toolbar

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder
    {
        val view  :View=LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int)
    {
        val currentUser=userList[position]
        holder.textName.text=currentUser.name

        holder.itemView.setOnClickListener{
            val intent=Intent(context,ChatActivity::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            intent.putExtra("message",currentUser.email)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int
    {
        return userList.size

    }

    class UserViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val textName=itemView.findViewById<TextView>(R.id.signUpTv)

    }



}