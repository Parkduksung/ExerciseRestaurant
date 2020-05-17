package com.work.restaurant.view.search.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.work.restaurant.view.search.bookmarks.SearchBookmarksFragment
import com.work.restaurant.view.search.rank.SearchRankFragment

class SearchViewPagerAdapter(
    supportFragmentManager: FragmentManager,
    private val tabList: List<String>
) :
    FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment =
        when (tabList[position]) {
            "운동맛집" -> {
                SearchRankFragment()
            }
            "관심맛집" -> {
                SearchBookmarksFragment()
            }
            else -> throw RuntimeException()
        }


    override fun getCount(): Int =
        tabList.size


    override fun getPageTitle(position: Int): CharSequence =
        tabList[position]
}