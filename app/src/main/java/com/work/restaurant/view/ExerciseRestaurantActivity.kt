package com.work.restaurant.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.work.restaurant.R
import com.work.restaurant.view.adapter.ViewPagerAdapter
import com.work.restaurant.view.loading.LoadingFragment
import kotlinx.android.synthetic.main.activity_main.*


class ExerciseRestaurantActivity : AppCompatActivity(), ExerciseRestaurantContract.View {


    private lateinit var presenter: ExerciseRestaurantContract.Presenter
    private val viewPagerAdapter by lazy {
        ViewPagerAdapter(
            this.supportFragmentManager,
            presenter.setFragmentMap()
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = ExerciseRestaurantPresenter()

        showLoading()
        showMain()

    }

//    private fun init() {

//        val fragmentMap: Map<String, Fragment> = mapOf(
//            resources.getStringArray(R.array.tab_main)[0] to HomeFragment(),
//            resources.getStringArray(R.array.tab_main)[1] to SearchFragment(),
//            resources.getStringArray(R.array.tab_main)[2] to CommunityFragment(),
//            resources.getStringArray(R.array.tab_main)[3] to MyPageFragment()
//        )

//
//        val adapter = ViewPagerAdapter(supportFragmentManager, fragmentMap)

//        val adapter = viewPagerAdapter
//
//
//        vp_main.adapter = adapter
//
//        tl_main.run {
//            setupWithViewPager(vp_main)
//            getTabAt(0)?.setIcon(R.drawable.ic_home)
//            getTabAt(1)?.setIcon(R.drawable.ic_search)
//            getTabAt(2)?.setIcon(R.drawable.ic_community)
//            getTabAt(3)?.setIcon(R.drawable.ic_mypage)
//        }

//    }

//    private fun loading() {
//        supportFragmentManager.beginTransaction()
//            .replace(
//                R.id.loading_container,
//                LoadingFragment()
//            ).commit()
//    }


//    override fun showLoading() {
//        supportFragmentManager.beginTransaction()
//            .replace(
//                R.id.loading_container,
//                LoadingFragment()
//            ).commit()
//    }
//
//    override fun showMain() {
//
//        val adapter = viewPagerAdapter
//
//        vp_main.adapter = adapter
//
//        tl_main.run {
//            setupWithViewPager(vp_main)
//            getTabAt(0)?.setIcon(R.drawable.ic_home)
//            getTabAt(1)?.setIcon(R.drawable.ic_search)
//            getTabAt(2)?.setIcon(R.drawable.ic_community)
//            getTabAt(3)?.setIcon(R.drawable.ic_mypage)
//        }
//
//    }


//    private fun loading() {
//        supportFragmentManager.beginTransaction()
//            .replace(
//                R.id.loading_container,
//                LoadingFragment()
//            ).commit()
//    }


    override fun showLoading() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.loading_container,
                LoadingFragment()
            ).commit()
    }

    override fun showMain() {
        val adapter = viewPagerAdapter
        presenter.setting(tl_main, vp_main, adapter)
    }


}


