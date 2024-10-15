package com.example.myapplication1

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Calls : Fragment() {
    private lateinit var callRecyclerView: RecyclerView
    private lateinit var adapter: CallAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calls, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)


        callRecyclerView = view.findViewById<RecyclerView>(R.id.callRecyclerView)
        val nameList=ArrayList<callModel>()
        nameList.add(callModel("Younes(2)","Today,10:30",R.drawable.avatar,R.drawable.baseline_call_received_24))
        nameList.add(callModel("Jonah(1)"," Today,11:40",R.drawable.img_1,R.drawable.baseline_call_made_24))
        nameList.add(callModel("Bartin(4)","Yesterday,12:40",R.drawable.img_2,R.drawable.baseline_call_received_24))
        nameList.add(callModel("Jennifer","Yesterday,13:30",R.drawable.img_3,R.drawable.baseline_call_made_24))
        nameList.add(callModel("Natasha(8)","Yesterday,14:50",R.drawable.img_4,R.drawable.baseline_call_received_24))
        nameList.add(callModel("david","Yesterday,15:31",R.drawable.img_5,R.drawable.baseline_call_made_24))

        callRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CallAdapter(requireContext(),nameList)
        adapter.notifyDataSetChanged()
        callRecyclerView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.call_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.calLog -> {
                Toast.makeText(requireContext(), "Call Log", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.settings -> {
                Log.d(TAG, "settings button clicked")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
}