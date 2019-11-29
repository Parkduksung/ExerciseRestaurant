package com.work.restaurant.view

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.work.restaurant.R
import com.work.restaurant.view.community.fragment.CommunityFragment
import com.work.restaurant.view.home.fragment.HomeFragment
import com.work.restaurant.view.mypage.fragment.MyPageFragment
import com.work.restaurant.view.search.fragment.SearchFragment

class ExerciseRestaurantPresenter() :
    ExerciseRestaurantContract.Presenter {


    @SuppressLint("ResourceType")
    override fun setting(
        tabLayout: TabLayout,
        viewPager: ViewPager,
        adapter: FragmentPagerAdapter
    ) {

        viewPager.adapter = adapter

        tabLayout.run {
            setupWithViewPager(viewPager)
            getTabAt(0)?.setIcon(R.drawable.ic_home)
            getTabAt(1)?.setIcon(R.drawable.ic_search)
            getTabAt(2)?.setIcon(R.drawable.ic_community)
            getTabAt(3)?.setIcon(R.drawable.ic_mypage)
        }


    }

    @SuppressLint("ResourceType")
    override fun setFragmentMap(): Map<String, Fragment> =
        mapOf(
            "홈" to HomeFragment(),
            "헬스장검색" to SearchFragment(),
            "커뮤니티" to CommunityFragment(),
            "마이페이지" to MyPageFragment()
        )

}



