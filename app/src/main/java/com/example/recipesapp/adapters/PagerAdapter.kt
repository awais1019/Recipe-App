package com.example.recipesapp.adapters

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(
    val  result:Bundle,
    val fragments:List<Fragment>,
    private val title:List<String>,
    activity:FragmentActivity
): FragmentStateAdapter(activity)
{
    override fun getItemCount()=fragments.size


    override fun createFragment(position: Int): Fragment {
        fragments[position].arguments=result
        return fragments[position]
    }




}