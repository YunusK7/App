package com.example.myapplication1

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication1.databinding.ActivitySignupBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

//import kotlinx.android.synthetic.main.activity_main.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding2: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference
    companion object {
        private const val TAG = "MainActivity2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Firebase.auth // Firebase'i başlatın
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        binding2 = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding2.root)


        supportActionBar?.hide()
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding2.button.setOnClickListener {
            val mail = binding2.editTextMail.text.toString().trim()
            val pass = binding2.editTextPassword.text.toString().trim()
            val name = binding2.editTextName.text.toString().trim()
            signUp(name,mail,pass)


            }
        }

    private fun signUp(name:String, mail:String,pass:String)
    {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }

        if (mail.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this,"Email or password is not provided",Toast.LENGTH_SHORT).show()
        } else if (!isValidEmail(mail)) {
            Toast.makeText(this,"Email address is badly formatted",Toast.LENGTH_SHORT).show()
            binding2.editTextMail.setError("Email address is badly formatted")
        } else {
            auth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        onBackPressed()
                        addUserToDatabase(name,mail,auth.currentUser?.uid!!)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun addUserToDatabase(name:String,mail: String,uid:String)
    {
        mDbRef=FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,mail,uid))

    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //reload()
            Toast.makeText(this@SignUpActivity, "Welcome to Sign Up Page", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
        return email.matches(emailRegex.toRegex())
    }



}