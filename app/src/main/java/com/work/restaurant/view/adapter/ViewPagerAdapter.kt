package com.work.restaurant.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


abstract class ViewPagerAdapter(
    supportFragmentManager: FragmentManager,
    private val tabList: List<String>
) :
    FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    abstract override fun getItem(position: Int): Fragment

    override fun getCount(): Int =
        tabList.size

    override fun getPageTitle(position: Int): CharSequence =
        tabList[position]
}

