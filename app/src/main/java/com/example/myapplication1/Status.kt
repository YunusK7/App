package com.example.myapplication1

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.databinding.FragmentStatusBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Status : Fragment() {
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_CAMERA_PERMISSION = 2
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: StatusAdapter
    private lateinit var userRecyclerView: RecyclerView

    // Declare the binding variable
    private var _binding: FragmentStatusBinding? = null
    private val binding get() = _binding!!
    // changes1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):  View? {
        val view = inflater.inflate(R.layout.fragment_status, container, false)

        val fabOpenCamera = view.findViewById<FloatingActionButton>(R.id.fButtonc)
        val statusCardView = view.findViewById<CardView>(R.id.status_cardview)

        fabOpenCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            } else {
                val intent:Intent = Intent("android.media.action.IMAGE_CAPTURE")
                startActivity(intent)
            }
        }

        statusCardView.setOnClickListener {
            val intent:Intent = Intent("android.media.action.IMAGE_CAPTURE")
            startActivity(intent)
        }
         return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        userRecyclerView = view.findViewById<RecyclerView>(R.id.userRecyclerview)
        val nameList=ArrayList<namesModel>()
        nameList.add(namesModel("Younes","10:30",R.drawable.avatar))
        nameList.add(namesModel("Jonah","09:40",R.drawable.img_1))
        nameList.add(namesModel("Bartin","Yesterday",R.drawable.img_2))
        nameList.add(namesModel("Jennifer","Yesterday",R.drawable.img_3))
        nameList.add(namesModel("Natasha","Yesterday",R.drawable.img_4))
        nameList.add(namesModel("Afrodit","Yesterday",R.drawable.img_5))
        userRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = StatusAdapter(requireContext(),nameList)
        adapter.notifyDataSetChanged()
        userRecyclerView.adapter = adapter

        adapter.onItemClick={
            val intent=Intent(requireContext(),userStatus::class.java)
            intent.putExtra( "name",it)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.status_menu,menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.privacy -> {
                Toast.makeText(requireContext(),"status privacy",Toast.LENGTH_SHORT).show()
                true
            }
            R.id.settings -> {
                Log.d(TAG, "Search button clicked")
                true
            }

        else-> super.onOptionsItemSelected(item)
    }


//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        // Set the binding to null to avoid memory leaks
//        _binding = null
//    }


    }
}
