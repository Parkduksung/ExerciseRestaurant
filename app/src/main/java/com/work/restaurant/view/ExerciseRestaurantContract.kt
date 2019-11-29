package com.work.restaurant.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

interface ExerciseRestaurantContract {

    interface View {

        fun showMain()
        fun showLoading()

    }


    interface Presenter {

        fun setFragmentMap(): Map<String, Fragment>


        fun setting(tabLayout: TabLayout, viewPager: ViewPager, adapter: FragmentPagerAdapter)

    }


}