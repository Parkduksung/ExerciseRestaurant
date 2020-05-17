package com.work.restaurant.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.work.restaurant.view.calendar.CalendarFragment
import com.work.restaurant.view.diary.main.DiaryFragment
import com.work.restaurant.view.home.main.HomeFragment
import com.work.restaurant.view.mypage.main.MyPageFragment
import com.work.restaurant.view.search.main.SearchFragment


class ViewPagerAdapter(
    supportFragmentManager: FragmentManager,
    private val tabList: List<String>
) :
    FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment =
        when (tabList[position]) {
            "홈" -> {
                HomeFragment()
            }
            "헬스장검색" -> {
                SearchFragment()
            }
            "다이어리" -> {
                DiaryFragment()
            }
            "켈린더" -> {
                CalendarFragment()
            }
            "마이페이지" -> {
                MyPageFragment()
            }
            else -> throw RuntimeException()
        }

    override fun getCount(): Int =
        tabList.size


    override fun getPageTitle(position: Int): CharSequence =
        tabList[position]
}

