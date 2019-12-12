package com.work.restaurant.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class ViewPagerAdapter(
    supportFragmentManager: FragmentManager,
    private val fragmentMap: Map<String, Fragment>
) :
    FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment =
        fragmentMap.map { it.value }[position]


    override fun getCount(): Int =
        fragmentMap.size


    override fun getPageTitle(position: Int): CharSequence =
        fragmentMap.map { it.key }[position]


}

