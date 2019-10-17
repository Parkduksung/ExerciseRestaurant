package com.work.restaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.work.restaurant.view.fragment.LoadingFragment
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loading()


        val fragmentAdapter = ViewPagerAdapter(supportFragmentManager)
        vp_main.adapter = fragmentAdapter

        tl_main.setupWithViewPager(vp_main)

        initTabIcon()


    }


    private fun initTabIcon() {


        tl_main.getTabAt(0)?.setIcon(R.drawable.ic_home)
        tl_main.getTabAt(1)?.setIcon(R.drawable.ic_search)
        tl_main.getTabAt(2)?.setIcon(R.drawable.ic_community)
        tl_main.getTabAt(3)?.setIcon(R.drawable.ic_mypage)


//        main_taps.getTabAt(1)?.apply {
//            icon?.setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);
//        }

    }

    private fun loading() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.loading_container, LoadingFragment()).commit()

    }


}


