package com.example.myapplication1

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VPAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position)
        {
            0 ->{ Chats() }
            1 ->{ Status()}
            2 ->{ Calls()}
            else ->{ throw Resources.NotFoundException("Position Not Found")}
        }
    }
}