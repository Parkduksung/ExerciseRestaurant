package com.work.restaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loading()


        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        main_viewPage.adapter = fragmentAdapter

        main_taps.setupWithViewPager(main_viewPage)

        initTabIcon()


    }


    private fun initTabIcon() {


        main_taps.getTabAt(0)?.setIcon(R.drawable.ic_home)
        main_taps.getTabAt(1)?.setIcon(R.drawable.ic_search)
        main_taps.getTabAt(2)?.setIcon(R.drawable.ic_community)
        main_taps.getTabAt(3)?.setIcon(R.drawable.ic_mypage)


//        main_taps.getTabAt(1)?.apply {
//            icon?.setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);
//        }

    }

    private fun loading() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.loading_container, LoadingFragment()).commit()

    }


}


