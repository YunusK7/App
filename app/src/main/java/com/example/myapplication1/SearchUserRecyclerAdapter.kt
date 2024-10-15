package com.example.myapplication1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchUserRecyclerAdapter(val context: Context,var myList: List<SearchData>):
    RecyclerView.Adapter<SearchUserRecyclerAdapter.SearchUserViewHolder>() {
    private lateinit var userList: ArrayList<User>

    inner class SearchUserViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val logo:ImageView=itemView.findViewById(R.id.profile_pic_imageView)
        val titleTv:TextView=itemView.findViewById(R.id.username_text)


    }

    fun setFilteredList(myList: List<SearchData>){
        this.myList=myList
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.search_user_recycler_row,parent,false)
        return SearchUserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
        holder.logo.setImageResource(myList[position].logo)
        holder.titleTv.text=myList[position].title


        userList= ArrayList()
//        userList==myList
//        val currentUser=userList[position]

        holder.itemView.setOnClickListener {
            val intent=Intent(context,ChatActivity::class.java)
            intent.putExtra("name",myList[position].title)
//            intent.putExtra("uid",currentUser.uid)
            context.startActivity(intent)
        }
    }

}