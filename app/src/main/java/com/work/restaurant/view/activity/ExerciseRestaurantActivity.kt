package com.work.restaurant.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.adapter.ViewPagerAdapter
import com.work.restaurant.view.fragment.community.fragment.CommunityFragment
import com.work.restaurant.view.fragment.home.main.HomeFragment
import com.work.restaurant.view.fragment.mypage.main.MyPageFragment
import com.work.restaurant.view.fragment.search.main.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*


class ExerciseRestaurantActivity : AppCompatActivity(),
    ExerciseRestaurantContract.View {

//    override fun onBackPressed() {
//        val fragmentList = supportFragmentManager.fragments
//
//        if (fragmentList != null) {
//            //TODO: Perform your logic to pass back press here
//            for (fragment in fragmentList) {
//                if (fragment is OnBackPressedListener) {
//                    (fragment as OnBackPressedListener).onBackPressed()
//                }
//            }
//        }
//    }

    private lateinit var presenter: ExerciseRestaurantContract.Presenter

    private var fragmentMap = emptyMap<String, Fragment>()

    private val viewPagerAdapter by lazy {
        ViewPagerAdapter(
            this.supportFragmentManager,
            fragmentMap
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = ExerciseRestaurantPresenter(this)
//        (fragmentMap.toList()[1].second as SearchFragment).logged()


        start()

    }

    private fun start() {
        presenter.init()
    }

    override fun showInit() {

        fragmentMap = mapOf(
            resources.getStringArray(R.array.tab_main)[0] to HomeFragment(),
            resources.getStringArray(R.array.tab_main)[1] to SearchFragment(),
            resources.getStringArray(R.array.tab_main)[2] to CommunityFragment(),
            resources.getStringArray(R.array.tab_main)[3] to MyPageFragment()
        )

        val adapter = viewPagerAdapter

        vp_main.adapter = adapter
        vp_main.offscreenPageLimit = 4

        tl_main.run {
            setupWithViewPager(vp_main)
            getTabAt(0)?.setIcon(R.drawable.ic_home)
            getTabAt(1)?.setIcon(R.drawable.ic_search)
            getTabAt(2)?.setIcon(R.drawable.ic_community)
            getTabAt(3)?.setIcon(R.drawable.ic_mypage)
        }

    }


}


