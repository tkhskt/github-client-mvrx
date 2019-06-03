package com.gericass.githubclientmvrx.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val arrayList = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        return arrayList[position]
    }

    fun addFragment(fragment: Fragment) {
        arrayList.add(fragment)
    }

    override fun getItemCount() = arrayList.size

}