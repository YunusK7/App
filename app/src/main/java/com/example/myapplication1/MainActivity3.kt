package com.example.myapplication1

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication1.databinding.ActivityMain3Binding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity3 : AppCompatActivity() {
    private lateinit var toolbar:Toolbar
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private val TAG = "MainActivity3"
    private lateinit var binding3: ActivityMain3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding3 = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding3.root)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewPager= binding3.viewPager
        tabLayout= binding3.tabLayout


        viewPager.adapter=VPAdapter(this)
        TabLayoutMediator(tabLayout,viewPager){ tab,position ->
            tab.text=when(position){
                0 -> {"Chats"}
                1 -> {"Status"}
                2 -> {"Calls"}
                else->{ throw Resources.NotFoundException("Position Not Found") }
            }
        }.attach()

        toolbar= findViewById(R.id.toolbar)
        toolbar.setTitle("Naabeer")
        setSupportActionBar(toolbar)


    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return try {
            menuInflater.inflate(R.menu.toolbar_menu, menu)
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error inflating menu", e)
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.camera -> {
                val intent: Intent = Intent("android.media.action.IMAGE_CAPTURE")
                startActivity(intent)
                true
            }
            R.id.main_search_button -> {
                Log.d(TAG, "Search button clicked")
                val intent= Intent(this, search_user_activity::class.java)
                startActivity(intent)
                Toast.makeText(this@MainActivity3, "Search Clicked", Toast.LENGTH_SHORT).show()
                true

            }

//            R.id.more -> {
//                Log.d(TAG, "More options clicked")
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}