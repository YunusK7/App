package com.example.myapplication1

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication1.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class MainActivity : AppCompatActivity() {

    private lateinit var binding1: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var rememberMeCheckBox: CheckBox
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        binding1 = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding1.root)
        enableEdgeToEdge()
         //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //auth=FirebaseAuth.getInstance()
//---------------------------------------------------------------------------
        mailEditText = binding1.editText1
        passwordEditText = binding1.editText2
        rememberMeCheckBox = binding1.checkBox
        loginButton = binding1.btnLogin

        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

        // Check if "Remember Me" is enabled
        if (sharedPreferences.getBoolean("rememberMe", false)) {
            val savedUsername = sharedPreferences.getString("username", "")
            val savedPassword = sharedPreferences.getString("password", "")

            mailEditText.setText(savedUsername)
            passwordEditText.setText(savedPassword)
            rememberMeCheckBox.isChecked = true
        }

// ---------------------------------------------------------------------------

        if (sharedPreferences.getBoolean("rememberMe", false)) {
            val savedUsername = sharedPreferences.getString("username", "")
            val savedPassword = sharedPreferences.getString("password", "")

            mailEditText.setText(savedUsername)
            passwordEditText.setText(savedPassword)
            rememberMeCheckBox.isChecked = true
        }

        supportActionBar?.hide()
        binding1.btnLogin.setOnClickListener{

                val intent2= Intent(this,MainActivity3::class.java)
                val mail = binding1.editText1.text.toString()
                val pass = binding1.editText2.text.toString()
                val keepLoggedIn: CheckBox = findViewById(R.id.checkBox)


            if (mail.isNullOrEmpty() || pass.isNullOrEmpty()) {
                Toast.makeText(
                    baseContext,
                    "Email or Password is not provided",
                    Toast.LENGTH_SHORT,
                ).show()
            }

            else {
                auth.signInWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            if (rememberMeCheckBox.isChecked) {
                                // Save login details
                                val editor = sharedPreferences.edit()
                                editor.putBoolean("rememberMe", true)
                                editor.putString("username", mail)
                                editor.putString("password", pass)
                                editor.apply()
                            } else {
                                // Clear login details
                                val editor = sharedPreferences.edit()
                                editor.clear()
                                editor.apply()
                            }

                            Toast.makeText(baseContext, "signInWithEmail:success", Toast.LENGTH_SHORT,).show()
                            val user = auth.currentUser
                            finish()
                            startActivity(intent2)
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }
        }

        binding1.btn2.setOnClickListener{
            val intent= Intent(this,SignUpActivity::class.java)
            startActivity(intent)
         }
//--------------------------------------------------------------------------

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //reload()
            Toast.makeText(this@MainActivity, "Welcome :)", Toast.LENGTH_SHORT).show()

        }
    }

}


