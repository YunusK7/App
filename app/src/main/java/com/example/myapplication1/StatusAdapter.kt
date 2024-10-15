package com.example.myapplication1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StatusAdapter(private val context: Context, private val nameList: List<namesModel>)
    :RecyclerView.Adapter<StatusAdapter.ViewHolder>(){

        var onItemClick: ((namesModel) ->Unit)?= null

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val statusName=itemView.findViewById<TextView>(R.id.signUpTv)
        val status=itemView.findViewById<TextView>(R.id.statusMessageTv)
        val image=itemView.findViewById<ImageView>(R.id.signUpImageView)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_status,parent,false))
    }

    override fun getItemCount(): Int {
        return nameList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val status=nameList[position]
        holder.statusName.text=status.statusName
        holder.status.text=status.status
        holder.image.setImageResource(status.image)


        holder.itemView.setOnClickListener {
//            val intent= Intent(context,userStatus::class.java)
//            context.startActivity(intent)
            onItemClick?.invoke(status)
        }

    }

}