package com.example.myapplication1

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
import java.util.Locale

class search_user_activity : AppCompatActivity() {
    private lateinit var searchInput: SearchView
    private lateinit var searchButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private var myList = ArrayList<SearchData>()
    private lateinit var adapter: SearchUserRecyclerAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList:ArrayList<Message>
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var arrowBack: ImageView
    var receiverRoom:String?=null
    var senderRoom:String?=null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//-----------------------------------------------------------------

//        chatRecyclerView=findViewById(R.id.chatRecyclerView)
//        messageBox=findViewById(R.id.messageBox)
//        sendButton=findViewById(R.id.sendButton)
//        arrowBack=findViewById(R.id.arrowBack)
//        val name=intent.getStringExtra("name")
//        val receiverUid=intent.getStringExtra("uid")
//
//        val senderUid= FirebaseAuth.getInstance().currentUser?.uid
//        mDbRef=FirebaseDatabase.getInstance().getReference()
//        senderRoom=receiverUid + senderUid
//        receiverRoom=senderUid + receiverUid
//
//        messageList= java.util.ArrayList()
//        messageAdapter=MessageAdapter(this,messageList)
//        chatRecyclerView.adapter?.notifyDataSetChanged()
//
//        chatRecyclerView.layoutManager=LinearLayoutManager(this)
//        chatRecyclerView.adapter=messageAdapter
//
//        mDbRef.child("chats").child(senderRoom!!).child("messages")
//            .addValueEventListener(object :ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    messageList.clear()
//                    for (postSnapshot in snapshot.children){
//                        val message= postSnapshot.getValue(Message::class.java)
//                        messageList.add(message!!)
//                    }
//                    messageAdapter.notifyDataSetChanged()
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//
//            })
//
//        arrowBack.setOnClickListener {
//            onBackPressed()
//        }
//
//
//        sendButton.setOnClickListener {
//            val message=messageBox.text.toString()
//            val messageObject=Message(message,senderUid)
//
//            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
//                .setValue(messageObject).addOnSuccessListener {
//
//                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
//                        .setValue(messageObject)
//                }
//            messageBox.setText("")
//
//        }
//

//-----------------------------------------------------------------


        searchInput = findViewById(R.id.search_username_input)
        searchButton = findViewById(R.id.search_user_btn)
        backButton = findViewById(R.id.back_button)
        recyclerView = findViewById(R.id.searh_user_recyclerView)

        auth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        myList = ArrayList()

        searchInput.requestFocus()

        adapter = SearchUserRecyclerAdapter(this,myList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        addDataToList()

        backButton.setOnClickListener {
            onBackPressed()
        }

        searchButton.setOnClickListener {

            var searchTerm: String = searchInput.query.toString()
            if (searchTerm.isEmpty() || searchTerm.length < 3) {
                setErrorOnSearchView(searchInput,"Invalid Username")
                return@setOnClickListener
            }
            setupSearchRecyclerView(searchTerm)
        }

        searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filterList(p0)
                return true
            }

        })
    }

    private fun filterList(query:String?):Boolean{
        if (query!=null){
            val filteredList=ArrayList<SearchData>()
            for (i in myList){
                if (i.title.toLowerCase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()){
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
            }else{
                adapter.setFilteredList(filteredList)
            }
        }
        return true
    }

    private fun addDataToList() {
        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myList.clear()
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if (auth.currentUser?.email != currentUser?.email) {
                        myList.add(SearchData(currentUser!!.name.toString(),R.drawable.avatar))
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error appropriately
                Log.e("LogFragment", "Database error: ${error.message}")
            }

        })
//        myList.add(SearchData(name,R.drawable.avatar))
    }

    fun setupSearchRecyclerView(searchTerm: String) {

    }

    private fun setErrorOnSearchView(searchView: SearchView, errorMessage: String) {
        val id = searchView.context
            .resources
            .getIdentifier("android:id/search_src_text", null, null)
        val editText = searchView.findViewById<EditText>(id)
        editText.error = errorMessage
    }


}