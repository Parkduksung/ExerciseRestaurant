package com.work.restaurant.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.adapter.ViewPagerAdapter
import com.work.restaurant.view.fragment.community.CommunityFragment
import com.work.restaurant.view.fragment.home.HomeAddressSelect1Fragment
import com.work.restaurant.view.fragment.home.HomeFragment
import com.work.restaurant.view.fragment.loading.LoadingFragment
import com.work.restaurant.view.fragment.mypage.MyPageFragment
import com.work.restaurant.view.fragment.search.SearchFragment
import com.work.restaurant.view.fragment.search.SearchRankFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , HomeAddressSelect1Fragment.OnFragmentInteractionListener {


    override fun getData(data: String) {


        val searchRankFragment  = SearchRankFragment()
        searchRankFragment.setTextView(data)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loading()
        init()


    }


    private fun init() {

        val fragmentMap: Map<String, Fragment> = mapOf(
            resources.getStringArray(R.array.tab_main)[0] to HomeFragment(),
            resources.getStringArray(R.array.tab_main)[1] to SearchFragment(),
            resources.getStringArray(R.array.tab_main)[2] to CommunityFragment(),
            resources.getStringArray(R.array.tab_main)[3] to MyPageFragment()
        )


        val adapter = ViewPagerAdapter(supportFragmentManager, fragmentMap)
        vp_main.adapter = adapter

        tl_main.run {
            setupWithViewPager(vp_main)
            getTabAt(0)?.setIcon(R.drawable.ic_home)
            getTabAt(1)?.setIcon(R.drawable.ic_search)
            getTabAt(2)?.setIcon(R.drawable.ic_community)
            getTabAt(3)?.setIcon(R.drawable.ic_mypage)
        }

    }

    private fun loading() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.loading_container,
                LoadingFragment()
            ).commit()
    }



}


