package com.example.myapplication1

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatActivity : AppCompatActivity() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var arrowBack: ImageView
    private lateinit var messageTV: TextView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList:ArrayList<Message>
    private lateinit var mDbRef:DatabaseReference
    private lateinit var softInputAssist: SoftInputAssist

    var receiverRoom:String?=null
    var senderRoom:String?=null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        softInputAssist=SoftInputAssist(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val name=intent.getStringExtra("name")
        val receiverUid=intent.getStringExtra("uid")

        toolbar.findViewById<TextView>(R.id.toolbar_title).text=name
        toolbar.findViewById<TextView>(R.id.toolbar_subtitle).text="Last seen today at 10:20"


        val senderUid= FirebaseAuth.getInstance().currentUser?.uid
        mDbRef=FirebaseDatabase.getInstance().getReference()
        senderRoom=receiverUid + senderUid
        receiverRoom=senderUid + receiverUid

        chatRecyclerView=findViewById(R.id.chatRecyclerView)
        messageBox=findViewById(R.id.messageBox)
        sendButton=findViewById(R.id.sendButton)
        arrowBack=findViewById(R.id.arrowBack)

        messageList= ArrayList()
        messageAdapter=MessageAdapter(this,messageList)
        chatRecyclerView.adapter?.notifyDataSetChanged()

        chatRecyclerView.layoutManager=LinearLayoutManager(this)
        chatRecyclerView.adapter=messageAdapter

//        val linearLayoutManager = LinearLayoutManager(this)
//        linearLayoutManager.reverseLayout = true
//        chatRecyclerView.layoutManager = linearLayoutManager




        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children){
                        val message= postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        arrowBack.setOnClickListener {
            onBackPressed()
        }


        sendButton.setOnClickListener {
            val message=messageBox.text.toString()
            val messageObject=Message(message,senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {

                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                    chatRecyclerView.scrollToPosition(messageList.size - 1)
                }
            messageBox.setText("")

        }
    }


    override fun onResume() {
        softInputAssist.onResume()
        super.onResume()
    }
    override fun onPause() {
        softInputAssist.onPause()
        super.onPause()
    }
    override fun onDestroy() {
        softInputAssist.onDestroy()
        super.onDestroy()
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return try {
            menuInflater.inflate(R.menu.chat_menu, menu)

            true
        } catch (e: Exception) {
            Log.e(TAG, "Error inflating menu", e)
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.videoCall -> {
                val intent: Intent = Intent("android.media.action.IMAGE_CAPTURE")
                startActivity(intent)
                true
            }
            R.id.voiceCall -> {
                Toast.makeText(this,"Voice call clicked",Toast.LENGTH_SHORT).show()
                true
            }

            R.id.clear -> {
                Toast.makeText(this,"chat cleaned",Toast.LENGTH_SHORT).show()
                messageList.clear()
                chatRecyclerView.adapter?.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}