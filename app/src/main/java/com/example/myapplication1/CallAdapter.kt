package com.example.myapplication1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CallAdapter (private val context: Context, private val nameList: List<callModel>)
    : RecyclerView.Adapter<CallAdapter.ViewHolder>(){

    var onItemClick: ((callModel) ->Unit)?= null

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val callName=itemView.findViewById<TextView>(R.id.callTV)
        val time=itemView.findViewById<TextView>(R.id.callMessageTv)
        val image=itemView.findViewById<ImageView>(R.id.callIv)
        val callImage=itemView.findViewById<ImageView>(R.id.imageViewCall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.call_persons,parent,false))
    }

    override fun getItemCount(): Int {
        return nameList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val call=nameList[position]
        holder.callName.text=call.callName
        holder.time.text=call.time
        holder.image.setImageResource(call.image)
        holder.callImage.setImageResource(call.callImage)


        holder.itemView.setOnClickListener {
//            val intent= Intent(context,userStatus::class.java)
//            context.startActivity(intent)
            onItemClick?.invoke(call)
        }

    }
}