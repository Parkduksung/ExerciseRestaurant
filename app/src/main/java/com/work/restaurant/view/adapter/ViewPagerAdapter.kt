package com.work.restaurant.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


@Suppress("DEPRECATION")
class ViewPagerAdapter(
    supportFragmentManager: FragmentManager,
    private val fragmentMap: Map<String, Fragment>
) :
    FragmentPagerAdapter(supportFragmentManager) {


    override fun getItem(position: Int): Fragment =
        fragmentMap.map { it.value }[position]


    override fun getCount(): Int =
        fragmentMap.size


    override fun getPageTitle(position: Int): CharSequence =
        fragmentMap.map { it.key }[position]


}

