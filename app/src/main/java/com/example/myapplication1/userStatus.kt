package com.example.myapplication1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class userStatus : AppCompatActivity() {
    private lateinit var statusPp:ImageView
    private lateinit var txtName:TextView
    private lateinit var txtTime:TextView
    private lateinit var statusPhoto:ImageView
    private lateinit var sendbutton:ImageView
    private lateinit var messageBox:EditText
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_status)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sendbutton=findViewById(R.id.sendButton)
        messageBox=findViewById(R.id.messageBox)

        messageBox.setOnClickListener {
            sendbutton.visibility=View.VISIBLE
        }
        sendbutton.setOnClickListener {
            messageBox.text.clear()
            Toast.makeText(this,"Sending reply",Toast.LENGTH_SHORT).show()
            finish()
        }


        val name=intent.getParcelableExtra<namesModel>("name")
        if (name!=null){
            statusPp=findViewById(R.id.signUpImageView)
            txtName=findViewById(R.id.textViewName)
            txtTime=findViewById(R.id.textViewTime)
            statusPhoto=findViewById(R.id.imageView6)

            statusPhoto.setOnClickListener {
                finish()
            }

            statusPp.setImageResource(name.image)
            txtName.text=name.statusName
            txtTime.text=name.status
            statusPhoto.setImageResource(name.image)
        }


    }
}